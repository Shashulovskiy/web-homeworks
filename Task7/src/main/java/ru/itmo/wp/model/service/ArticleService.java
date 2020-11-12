package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;

import java.util.List;

public class ArticleService {
    private final ArticleRepository articleRepository = new ArticleRepositoryImpl();

    public void validateAndSaveArticle(Article article) throws ValidationException {
        if (article.getTitle().length() < 3) {
            throw new ValidationException("Article title too short");
        }
        if (article.getText().isEmpty()) {
            throw new ValidationException("Article's body cannot be empty.");
        }
        if (article.getText().length() > 4000) {
            throw new ValidationException("Article too long");
        }
        articleRepository.save(article);
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public List<Article> findAllByUserId(Long userId) {
        return articleRepository.findAllByUserId(userId);
    }

    public Article find(Long id) {
        return articleRepository.find(id);
    }

    public Article toggleHidden(Long id) {
        Article article = articleRepository.find(id);
        articleRepository.updateHidden(id, !article.getHidden());
        article.setHidden(!article.getHidden());
        return article;
    }
}
