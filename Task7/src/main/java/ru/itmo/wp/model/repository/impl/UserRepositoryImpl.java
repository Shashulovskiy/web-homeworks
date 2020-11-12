package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepositoryImpl extends BasicRepositoryImpl<Long, User> implements UserRepository {
    @Override
    public User findByLogin(String login) {
        Map<String, Object> values = new HashMap<>();
        values.put("login", login);

        return findBy(values);
    }

    @Override
    public User findByLoginAndPasswordSha(String loginOrEmail, String passwordSha) {
        Map<String, Object> values = new HashMap<>();
        values.put("login", loginOrEmail);
        values.put("passwordSha", passwordSha);

        return findBy(values);
    }

    @Override
    public User findByEmailAndPasswordSha(String loginOrEmail, String passwordSha) {
        Map<String, Object> values = new HashMap<>();
        values.put("email", loginOrEmail);
        values.put("passwordSha", passwordSha);

        return findBy(values);
    }

    @Override
    public User findByEmail(String email) {
        Map<String, Object> values = new HashMap<>();
        values.put("email", email);

        return findBy(values);
    }

    @Override
    public void save(User user, String passwordSha) {
        List<String> fieldNames = new ArrayList<>();
        List<Object> values = new ArrayList<>();

        fieldNames.add("passwordSha");
        values.add(passwordSha);

        save(user, fieldNames, values);
    }

    @Override
    public void updateAdmin(Long id, boolean admin) {
        Map<String, Object> values = new HashMap<>();
        values.put("admin", admin);
        update(id, values);
    }
}
