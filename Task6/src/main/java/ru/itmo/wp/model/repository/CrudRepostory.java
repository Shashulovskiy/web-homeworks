package ru.itmo.wp.model.repository;

import java.util.List;

public interface CrudRepostory<ID, T> {
    T find(ID id);
    List<T> findAll();
    <S extends T> void save(S entity);
    Long findCount();
}
