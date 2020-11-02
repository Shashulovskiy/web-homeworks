package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.TalkRepository;
import ru.itmo.wp.model.repository.impl.TalkRepositoryImpl;

import java.util.List;

public class TalkService {
    private final TalkRepository talkRepository = new TalkRepositoryImpl();

    public void sendTalk(Talk talk) {
        talkRepository.save(talk);
    }

    public List<Talk> findAll() {
        return talkRepository.findAll();
    }

    public void validateMessage(String message, Long targetUser) throws ValidationException {
        if (message.length() > 80) {
            throw new ValidationException("Message cannot me longer than 80 characters");
        }

        if (message.isEmpty()) {
            throw new ValidationException("Message cannot be empty");
        }

        if (talkRepository.find(targetUser) == null) {
            throw new ValidationException("User you are trying to send message to does not exist");
        }
    }
}
