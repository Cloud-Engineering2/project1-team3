package com.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PostViewController {
    @GetMapping("/posts")
    public String showPosts(Model model) {
        return "post/posts";
    }

    @GetMapping("/posts/{pid}")
    public String showPostDetail(@PathVariable Long pid, Model model) {
        model.addAttribute("postId", pid);
        return "post/postDetail";
    }
}
