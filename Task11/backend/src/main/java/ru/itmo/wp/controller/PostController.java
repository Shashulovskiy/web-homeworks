package ru.itmo.wp.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.exception.EntityNotFoundException;
import ru.itmo.wp.exception.ValidationException;
import ru.itmo.wp.form.CommentForm;
import ru.itmo.wp.form.NewPostRequest;
import ru.itmo.wp.form.validator.PostFormValidator;
import ru.itmo.wp.service.CommentService;
import ru.itmo.wp.service.JwtService;
import ru.itmo.wp.service.PostService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/1")
public class PostController {
    private final PostService postService;
    private final JwtService jwtService;
    private final CommentService commentService;
    private final PostFormValidator postFormValidator;

    public PostController(PostService postService, JwtService jwtService, CommentService commentService, PostFormValidator postFormValidator) {
        this.postService = postService;
        this.jwtService = jwtService;
        this.commentService = commentService;
        this.postFormValidator = postFormValidator;
    }

    @InitBinder("newPostRequest")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(postFormValidator);
    }

    @GetMapping("posts")
    public List<Post> findPosts() {
        return postService.findAll();
    }

    @PostMapping("posts")
    public Post addPost(@RequestBody @Valid NewPostRequest newPostRequest, BindingResult bindingResult) {
        User user = jwtService.find(newPostRequest.getJwt());
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
        Post post = new Post();
        post.setText(newPostRequest.getText());
        post.setTitle(newPostRequest.getTitle());
        post.setUser(user);
        return postService.addPost(post);
    }

    @PostMapping("post/{id}/comments")
    public Comment addComment(@PathVariable String id, @RequestBody @Valid CommentForm commentForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        User user = jwtService.find(commentForm.getJwt());
        Post post;
        try {
            post = postService.findById(Long.valueOf(id));
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid post id");
        }
        if (post == null) {
            throw new EntityNotFoundException("Post does not exist!");
        }
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setText(commentForm.getText());
        comment.setPost(post);

        return commentService.addComment(comment);
    }

    @GetMapping("post/{id}")
    public Post getPost(@PathVariable String id) {
        Post post;
        try {
            post = postService.findById(Long.valueOf(id));
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid post id");
        }
        if (post == null) {
            throw new EntityNotFoundException("Post does not exist!");
        }
        return post;
    }
}
