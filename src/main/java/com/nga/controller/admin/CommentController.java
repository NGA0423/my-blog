package com.nga.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/comment")
public class CommentController {
    @GetMapping("/index")
    public String index(){
        return "admin/comment-management";
    }
}
