package com.nga.controller.admin;

import com.github.pagehelper.PageInfo;
import com.nga.constan.LogActions;
import com.nga.controller.BaseErrorController;
import com.nga.dao.ContentDAO;
import com.nga.dao.cond.ContentCond;
import com.nga.service.ArticleService;
import com.nga.service.ContentService;
import com.nga.service.LogService;
import com.nga.util.APIResponse;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/admin/article")
public class ArticleController extends BaseErrorController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ContentService contentService;
    @Autowired
    private LogService logService;

    /**
     * 文章管理
     *
     * @return
     */
    @GetMapping("/management")
    public String index(HttpServletRequest request, @ApiParam(name = "page", value = "页数", required = false) @RequestParam(name = "page", required = false, defaultValue = "1") int page, @ApiParam(name = "limit", value = "每页数量", required = false) @RequestParam(name = "limit", required = false, defaultValue = "15") int limit) {
        PageInfo<ContentDAO> byCond = articleService.getArticlesByCond(new ContentCond(), page, limit);
        request.setAttribute("byCond", byCond);
        return "admin/article-management";
    }

    /**
     * 文章发布
     *
     * @return
     */
    @GetMapping("/publish")
    public String publishArticle() {
        return "admin/article";
    }

    @GetMapping("/delete")
    @ResponseBody
    public APIResponse deleteArticle(@ApiParam(name = "cid",value = "文章主键",required = true)
                                     @RequestParam(name = "cid",required = true)
                                     Integer cid,
                                     HttpServletRequest request) {
        contentService.deleteArticleById(cid);
        logService.addLog(LogActions.DEL_ARTICLE.getAction(),cid+"",request.getRemoteAddr(),this.getUid(request));
        return APIResponse.success();
    }
}
