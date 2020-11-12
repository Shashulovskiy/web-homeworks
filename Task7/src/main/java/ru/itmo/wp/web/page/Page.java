package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class Page {
    private final UserService userService = new UserService();

    protected User getUser(HttpServletRequest request) {
        User authorizedUser = (User) request.getSession().getAttribute("user");
        if (authorizedUser == null) {
            return null;
        }
        return userService.find(authorizedUser.getId());
    }
}
