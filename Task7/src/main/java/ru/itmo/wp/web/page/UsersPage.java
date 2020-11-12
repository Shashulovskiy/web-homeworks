package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.annotation.Json;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class UsersPage extends Page {
    private final UserService userService = new UserService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }

    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", userService.findAll());
    }

    private void findUser(HttpServletRequest request, Map<String, Object> view) {
        view.put("user",
                userService.find(Long.parseLong(request.getParameter("userId"))));
    }

    @Json
    public void findLoginById(HttpServletRequest request, Map<String, Object> view) {
        view.put("userLogin", userService.findLoginById(Long.parseLong(request.getParameter("userId"))));
    }

    @Json
    private void toggleAdmin(HttpServletRequest request, Map<String, Object> view) {
        if (!userService.find(getUser(request).getId()).getAdmin()) {
            request.getSession().setAttribute("message", "You dont have permission to perform this action");
            throw new RedirectException("/index");
        }
        User user = userService.toggleAdmin(Long.parseLong(request.getParameter("id")));
        view.put("user", user);
    }

    @Json
    private void checkAdmin(HttpServletRequest request, Map<String, Object> view) {
        User user = userService.find(Long.parseLong(request.getParameter("id")));
        view.put("isAdmin", user.getAdmin());
    }
}
