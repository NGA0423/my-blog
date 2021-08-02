package com.nga.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin/article")
public class ArticleController {
    /**
     * 文章管理
     * @return
     */
    @GetMapping("/management")
    public String index(){
        return "admin/article-management";
    }

    /**
     * 文章发布
     * @return
     */
    @GetMapping("/publish")
    public String publishArticle(){
        return "admin/article";
    }
}
