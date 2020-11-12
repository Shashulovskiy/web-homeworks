package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class MyArticlesPage extends Page {
    private final ArticleService articleService = new ArticleService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        if (getUser(request) == null) {
            request.getSession().setAttribute("message", "You need to be authorized to visit this page.");
            throw new RedirectException("/index");
        }
        view.put("articles", articleService.findAllByUserId(((User)request.getSession().getAttribute("user")).getId()));
    }
}
