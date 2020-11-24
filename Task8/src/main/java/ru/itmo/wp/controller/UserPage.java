package ru.itmo.wp.controller;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.exceptions.UserNotFoundException;
import ru.itmo.wp.service.NoticeService;
import ru.itmo.wp.service.UserService;

@Controller
public class UserPage extends Page {

    private final UserService userService;
    private final NoticeService noticeService;

    public UserPage(UserService userService, NoticeService noticeService) {
        this.userService = userService;
        this.noticeService = noticeService;
    }

    @GetMapping("/user/{id}")
    public String userPage(@PathVariable String id, Model model) throws UserNotFoundException {
        User user = userService.findById(Long.parseLong(id));
        if (user == null) {
            throw new UserNotFoundException();
        }
        model.addAttribute("user", user);
        return "UserPage";
    }

    @GetMapping({"{/user/", "/user"})
    public String userRootPage() throws NumberFormatException {
        throw new NumberFormatException();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public String handleNumberFormat(Model model) {
        model.addAttribute("notices", noticeService.findAll());
        model.addAttribute("error", "Invalid user id format");
        return "UserPage";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFound(Model model) {
        model.addAttribute("notices", noticeService.findAll());
        model.addAttribute("error", "User not found");
        return "UserPage";
    }
}
