package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.TalkService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TalksPage extends Page {
    private final TalkService talkService = new TalkService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        if (getUser() == null) {
            setMessage("You need to be authorized to view this page");
            throw new RedirectException("/index");
        }
        List<User> users = userService.findAll();
        view.put("users", users);
        view.put("talks", talkService.findAll().stream().filter(talk -> talk.getSourceUserId().equals(getUser().getId())
                || talk.getTargetUserId().equals(getUser().getId())).collect(Collectors.toList()));
        Map<Long, String> idToUserName = new HashMap<>();
        for (User user : users) {
            idToUserName.put(user.getId(), user.getLogin());
        }
        view.put("idToUserName", idToUserName);
    }

    private void send(HttpServletRequest request, Map<String, Object> view) {
        Talk talk = new Talk();
        try {
            talkService.validateMessage(request.getParameter("message"), Long.parseLong(request.getParameter("target")));
            talk.setSourceUserId(getUser().getId());
            talk.setTargetUserId(Long.parseLong(request.getParameter("target")));
            talk.setMessage(request.getParameter("message"));

            talkService.sendTalk(talk);
        } catch (ValidationException e) {
            setMessage("Message too long");
        }
        throw new RedirectException("/talks");
    }
}
