package com.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostViewController {
    @GetMapping("/posts")
    public String showPosts(Model model) {
        return "post/posts";
    }
}
