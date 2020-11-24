package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.UserToggleRequest;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UsersPage extends Page {
    private final UserService userService;

    public UsersPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/all")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        return "UsersPage";
    }

    @PostMapping("/toggleUser")
    public String toggleUser(@Valid @ModelAttribute("userToggleRequest") UserToggleRequest userToggleRequest,
                             Model model,
                             HttpSession session) {
        User user = getUser(session);
        if (user == null || user.getDisabled()) {
            putMessage(session, "Access denied");
            return "redirect:";
        }
        userService.updateStatus(userToggleRequest.getId(), userToggleRequest.getDisabled());
        model.addAttribute("users", userService.findAll());
        return "redirect:/users/all";
    }
}
