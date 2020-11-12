package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.repository.ArticleRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleRepositoryImpl extends BasicRepositoryImpl<Long, Article> implements ArticleRepository {
    @Override
    public List<Article> findAllByUserId(Long userId) {
        Map<String, Object> values = new HashMap<>();
        values.put("userId", userId);
        return findAllBy(values);
    }

    @Override
    public void updateHidden(Long id, boolean hidden) {
        Map<String, Object> values = new HashMap<>();
        values.put("hidden", hidden);
        update(id, values);
    }
}
