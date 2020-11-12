package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Article;

import java.util.List;

public interface ArticleRepository extends CrudRepostory<Long, Article> {
    List<Article> findAllByUserId(Long userId);
    void updateHidden(Long id, boolean hidden);
}
