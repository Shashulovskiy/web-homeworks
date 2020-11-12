package ru.itmo.wp.web.page;


import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.annotation.Json;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ArticlePage extends Page {
    private final ArticleService articleService = new ArticleService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        if (getUser(request) == null) {
            request.getSession().setAttribute("message", "You need to be authorized to visit this page.");
            throw new RedirectException("/index");
        }
    }

    @Json
    private void addArticle(HttpServletRequest request, Map<String, Object> view) throws ValidationException {

        Article article = new Article();
        article.setUserId(((User) request.getSession().getAttribute("user")).getId());
        article.setTitle(request.getParameter("title"));
        article.setText(request.getParameter("text"));
        article.setHidden(false);

        articleService.validateAndSaveArticle(article);
        throw new RedirectException("/index");
    }

    @Json
    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("articles", articleService.findAll());
    }

    @Json
    private void toggleHidden(HttpServletRequest request, Map<String, Object> view) {
        User authorizedUser = getUser(request);
        if (authorizedUser == null) {
            request.getSession().setAttribute("message", "You need to be authorized to perform this action.");
            throw new RedirectException("/index");
        }
        if (!authorizedUser.getId().equals(articleService.find(Long.parseLong(request.getParameter("id"))).getUserId())) {
            request.getSession().setAttribute("message", "You don't have the rights to edit this article.");
            throw new RedirectException("/index");
        }
        Article article = articleService.toggleHidden(Long.parseLong(request.getParameter("id")));
        view.put("article", article);
    }
}
