package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Talk;

import java.util.List;

public interface TalkRepository {
    <S extends Talk> void save(S talk);
    Talk find(Long id);
    List<Talk> findAll();
}
