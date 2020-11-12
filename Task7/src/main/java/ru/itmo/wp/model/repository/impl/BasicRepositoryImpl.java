package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.annotations.CreationTime;
import ru.itmo.wp.model.annotations.Id;
import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.CrudRepostory;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.Date;
import java.util.*;
import java.util.stream.Collectors;

public abstract class BasicRepositoryImpl<ID, T> implements CrudRepostory<ID, T> {
    private final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    private String getDataTableName() {
        return Objects.requireNonNull(getInstance()).getClass().getSimpleName();
    }

    private <S> S executeDatabaseQuery(String query, SQLStatementOperation<S, PreparedStatement> function) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                return function.apply(statement);
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't access " + getDataTableName() + ".", e);
        }
    }

    @Override
    public T find(ID id) {
        Map<String, Object> values = new HashMap<>();
        values.put("id", id);

        return findBy(values);
    }

    public T findBy(Map<String, Object> values) {
        String query = "SELECT * FROM " + getDataTableName() + " WHERE " +
                values.keySet().stream().map(i -> i + "=?").collect(Collectors.joining("AND "));
        return executeDatabaseQuery(query, statement -> {
            int i = 1;
            for (Object value : values.values()) {
                statement.setObject(i++, value);
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                return toType(statement.getMetaData(), resultSet);
            }
        });
    }

    @Override
    public List<T> findAll() {
        return findAllBy(new HashMap<>());
    }

    public List<T> findAllBy(Map<String, Object> values) {
        final List<T> talks = new ArrayList<>();
        String parameterQueries = values.keySet().stream().map(i -> i + "=?").collect(Collectors.joining("AND "));
        String query = "SELECT * FROM " +
                getDataTableName() +
                (values.size() == 0 ? " " : " WHERE ") +
                parameterQueries +
                " ORDER BY id DESC";
        executeDatabaseQuery(query, statement -> {
            int i = 1;
            for (Object value: values.values()) {
                statement.setObject(i++, value);
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                T typeInstance;
                while ((typeInstance = toType(statement.getMetaData(), resultSet)) != null) {
                    talks.add(typeInstance);
                }
            }
            return null; // Void function
        });
        return talks;
    }

    public void update(ID id, Map<String, Object> values) {
        String parameterQueries = values.keySet().stream().map(i -> i + "=?").collect(Collectors.joining("AND "));
        String query = "UPDATE " + getDataTableName() + " SET " + parameterQueries + " WHERE id=" + id;
        executeDatabaseQuery(query, statement -> {
            int i = 1;
            for (Object value: values.values()) {
                statement.setObject(i++, value);
            }
            statement.executeQuery();
            return null; // Void function
        });
    }

    @Override
    public <S extends T> void save(S entity) {
        save(entity, new ArrayList<>(), new ArrayList<>());
    }

    public <S extends T> void save(S entity, List<String> fieldNames, List<Object> values) {
        for (Method method : entity.getClass().getDeclaredMethods()) {
            String methodName = method.getName();
            if (methodName.startsWith("get") && !methodName.equals("get" + capitalize(getIdFieldName()))) {
                String parameterName = toCamelCase(methodName.substring(3));
                fieldNames.add(parameterName);
                method.setAccessible(true);
                try {
                    if (!methodName.equals("get" + capitalize(getCreationTimeFieldName()))) {
                        values.add(method.invoke(entity));
                    }
                } catch (Exception e) {
                    // Unreachable
                }
            }
        }

        String fieldNamesQueryString = fieldNames.stream().map(i -> '`' + i + '`').collect(Collectors.joining(", "));
        String substitutionQueryString = fieldNames.stream().map(i -> i.equals(getCreationTimeFieldName()) ? "NOW()" : "?").collect(Collectors.joining(", "));
        String query = String.format("INSERT INTO `%s` (%s) VALUES (%s)", getDataTableName(), fieldNamesQueryString, substitutionQueryString);
        executeDatabaseQuery(query, statement -> {
            for (int i = 1; i <= values.size(); ++i) {
                statement.setObject(i, values.get(i - 1));
            }
            if (statement.executeUpdate() != 1) {
                throw new RepositoryException("Can't save Event.");
            } else {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    setAutogeneratedProperties(entity, generatedKeys);
                } else {
                    throw new RepositoryException("Can't save " + getDataTableName() + " [no autogenerated fields].");
                }
            }
            return null; // Void function
        });
    }

    @SuppressWarnings("unchecked")
    private <S extends T> void setAutogeneratedProperties(S entity, ResultSet resultSet) {
        String idField = capitalize(getIdFieldName());
        String creationTimeField = capitalize(getCreationTimeFieldName());
        try {
            Method idSetter = entity.getClass().getDeclaredMethod("set" + idField, getIdClass());
            idSetter.setAccessible(true);

            Method idGetter = entity.getClass().getDeclaredMethod("get" + idField);
            idSetter.setAccessible(true);

            Method creationTimeSetter = entity.getClass().getDeclaredMethod("set" + creationTimeField, Date.class);
            idSetter.setAccessible(true);

            Method creationTimeGetter = entity.getClass().getDeclaredMethod("get" + creationTimeField);
            idSetter.setAccessible(true);

            idSetter.invoke(entity, resultSet.getLong(1));
            creationTimeSetter.invoke(entity, (Date) creationTimeGetter.invoke(find((ID) idGetter.invoke(entity))));
        } catch (Exception e) {
            throw new RepositoryException("Unable to set autogenerated properties. Make sure your POJO contains id/creationTime fields", e);
        }
    }

    private T toType(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        T entity = getInstance();
        Map<String, Method> setters = new HashMap<>();
        Arrays.stream(Objects.requireNonNull(entity).getClass().getDeclaredMethods())
                .filter(s -> s.getName().startsWith("set"))
                .forEach(method -> setters.put(toCamelCase(method.getName().substring(3)), method));
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            if (setters.containsKey(metaData.getColumnName(i))) {
                try {
                    setters.get(metaData.getColumnName(i)).invoke(entity, resultSet.getObject(i));
                } catch (Exception e) {
                    // Unreachable
                }
            }
        }

        return entity;
    }

    @Override
    public Long findCount() {
        return (Long) executeDatabaseQuery("SELECT COUNT(*) FROM " + getDataTableName(), statement -> {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return 0;
                }
                return resultSet.getObject(1);
            }
        });
    }

    @SuppressWarnings("unchecked")
    private T getInstance() {
        try {
            return ((Class<T>)((ParameterizedType) Objects.requireNonNull(this.getClass()).
                    getGenericSuperclass()).getActualTypeArguments()[1]).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            // Unreachable
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private Class<ID> getIdClass() {
        try {
            return (Class<ID>) ((ParameterizedType) Objects.requireNonNull(this.getClass()).
                    getGenericSuperclass()).getActualTypeArguments()[0];
        } catch (Exception e) {
            // Unreachable
        }
        return null;
    }

    private String getIdFieldName() {
        return getFieldByAnnotation(Id.class, "id");
    }

    private String getCreationTimeFieldName() {
        return getFieldByAnnotation(CreationTime.class, "creationTime");
    }

    private String getFieldByAnnotation(Class<? extends java.lang.annotation.Annotation> annotation, String def) {
        T instance = getInstance();
        for(Field field : Objects.requireNonNull(instance).getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(annotation)) {
                return field.getName();
            }
        }
        return def;
    }

    private String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    private String toCamelCase(String s) {
        return s.substring(0, 1).toLowerCase() + s.substring(1);
    }

    @FunctionalInterface
    private interface SQLStatementOperation<S, V> {
        S apply(V action) throws SQLException;
    }
}
