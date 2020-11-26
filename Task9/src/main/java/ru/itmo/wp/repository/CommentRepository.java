package ru.itmo.wp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Iterable<Comment> findAllByPost(Post post);
}
