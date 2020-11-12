package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.User;

import java.util.List;

public interface UserRepository {
    User find(Long id);
    User findByLogin(String login);
    User findByLoginAndPasswordSha(String login, String passwordSha);
    User findByEmailAndPasswordSha(String loginOrEmail, String passwordSha);
    User findByEmail(String email);
    List<User> findAll();
    Long findCount();
    void updateAdmin(Long id, boolean admin);

    void save(User user, String passwordSha);
}
