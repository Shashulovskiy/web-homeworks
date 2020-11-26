package ru.itmo.wp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.repository.CommentRepository;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Iterable<Comment> finaAll() {
        return commentRepository.findAll();
    }

    public Iterable<Comment> findAllByPost(Post post) {
        return commentRepository.findAllByPost(post);
    }

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }
}
