package ru.itmo.wp.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.repository.exceptions.PostNotFoundException;
import ru.itmo.wp.security.Guest;
import ru.itmo.wp.service.CommentService;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class PostPage extends Page{

    private final PostService postService;
    private final CommentService commentService;

    public PostPage(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @Guest
    @GetMapping("/post/{id}")
    public String getPost(@PathVariable String id, Model model) throws PostNotFoundException {
        Post post = postService.findById(Long.valueOf(id));
        if (post == null) {
            throw new PostNotFoundException();
        }
        model.addAttribute("post", post);
        model.addAttribute("comments", commentService.findAllByPost(post));
        model.addAttribute("comment", new Comment());
        return "PostPage";
    }

    @Guest
    @GetMapping({"/post", "/post/"})
    public String getPost() {
        throw new NumberFormatException();
    }

    @PostMapping("/post/{id}")
    public String addComment(@PathVariable String id,
                             @Valid @ModelAttribute("comment") Comment comment,
                             BindingResult bindingResult,
                             HttpSession session,
                             Model model) {
        Post post = postService.findById(Long.valueOf(id));
        if (bindingResult.hasErrors()) {
            model.addAttribute("post", post);
            return "PostPage";
        }
        comment.setUser(getUser(session));
        comment.setPost(post);
        commentService.save(comment);
        return "redirect:/post/" + comment.getPost().getId();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrityViolation(Model model) {
        model.addAttribute("error", "Invalid characters");
        return "PostPage";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public String handleNumberFormat(Model model) {
        model.addAttribute("error", "Invalid post id");
        return "PostPage";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PostNotFoundException.class)
    public String handlePostNotFound(Model model) {
        model.addAttribute("error", "Post does not exist");
        return "PostPage";
    }
}
