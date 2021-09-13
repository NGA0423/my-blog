package com.nga.controller.admin;

import com.github.pagehelper.PageInfo;
import com.nga.constan.LogActions;
import com.nga.controller.BaseErrorController;
import com.nga.dao.ContentDAO;
import com.nga.dao.MetaDAO;
import com.nga.dao.cond.ContentCond;
import com.nga.dao.cond.MetaCond;
import com.nga.service.ArticleService;
import com.nga.service.ContentService;
import com.nga.service.LogService;
import com.nga.service.MetaService;
import com.nga.util.APIResponse;
import com.nga.util.TypesUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.Period;
import java.util.List;


@Controller
@RequestMapping("/admin/article")
public class ArticleController extends BaseErrorController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ContentService contentService;
    @Autowired
    private LogService logService;

    @Autowired
    private MetaService metaService;

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

    @ApiOperation("发布文章页")
    @GetMapping("/publish")
    public String newArticle(HttpServletRequest request){
        MetaCond metaCond = new MetaCond();
        metaCond.setType(TypesUtil.CATEGORY.getType());
        List<MetaDAO> metas = metaService.getMetas(metaCond);
        request.setAttribute("categories",metas);
        return "admin/article";
    }
    /**
     * 文章发布
     *
     * @return
     */
    @ApiOperation("发布新文章")
    @PostMapping("/publish")
    @ResponseBody
    public APIResponse publishArticle(HttpServletRequest request,
                                 @ApiParam(name = "cid", value = "文章主键", required = true)
                                 @RequestParam(name = "cid", required = true) Integer cid,
                                 @ApiParam(name = "title", value = "标题", required = true)
                                 @RequestParam(name = "title", required = true)
                                         String title,
                                 @ApiParam(name = "titlePic", value = "标题图片", required = false)
                                 @RequestParam(name = "titlePic", required = false)
                                         String titlePic,
                                 @ApiParam(name = "slug", value = "内容缩略名", required = false)
                                 @RequestParam(name = "slug", required = false)
                                         String slug,
                                 @ApiParam(name = "content", value = "内容", required = true)
                                 @RequestParam(name = "content", required = true)
                                         String content,
                                 @ApiParam(name = "type", value = "文章类型", required = true)
                                 @RequestParam(name = "type", required = true)
                                         String type,
                                 @ApiParam(name = "status", value = "文章状态", required = true)
                                 @RequestParam(name = "status", required = true)
                                         String status,
                                 @ApiParam(name = "tags", value = "标签", required = false)
                                 @RequestParam(name = "tags", required = false)
                                         String tags,
                                 @ApiParam(name = "categories", value = "分类", required = false)
                                 @RequestParam(name = "categories", required = false, defaultValue = "默认分类")
                                         String categories,
                                 @ApiParam(name = "allowComment", value = "是否允许评论", required = true)
                                 @RequestParam(name = "allowComment", required = true)
                                         Boolean allowComment) {
        ContentDAO contentDAO = new ContentDAO();
        contentDAO.setTitle(title);
        contentDAO.setTitlePic(titlePic);
        contentDAO.setSlug(slug);
        contentDAO.setContent(content);
        contentDAO.setType(type);
        contentDAO.setStatus(status);
        contentDAO.setTags(type.equals(TypesUtil.ARTICLE.getType()) ? tags : null);
        // 只允许博客文章有分类，防止作品被收入分类
        contentDAO.setCategories(type.equals(TypesUtil.ARTICLE.getType()) ? categories : null);
        contentDAO.setAllowComment(allowComment ? 1 : 0);
        contentService.addArticle(contentDAO);

        return APIResponse.success();
    }

    @ApiOperation("编辑保存文章")
    @PostMapping("/modify")
    @ResponseBody
    public APIResponse modifyArticle(HttpServletRequest request,
                                     @ApiParam(name = "cid", value = "文章主键", required = true)
                                     @RequestParam(name = "cid", required = true) Integer cid,
                                     @ApiParam(name = "title", value = "标题", required = true)
                                     @RequestParam(name = "title", required = true)
                                             String title,
                                     @ApiParam(name = "titlePic", value = "标题图片", required = false)
                                     @RequestParam(name = "titlePic", required = false)
                                             String titlePic,
                                     @ApiParam(name = "slug", value = "内容缩略名", required = false)
                                     @RequestParam(name = "slug", required = false)
                                             String slug,
                                     @ApiParam(name = "content", value = "内容", required = true)
                                     @RequestParam(name = "content", required = true)
                                             String content,
                                     @ApiParam(name = "type", value = "文章类型", required = true)
                                     @RequestParam(name = "type", required = true)
                                             String type,
                                     @ApiParam(name = "status", value = "文章状态", required = true)
                                     @RequestParam(name = "status", required = true)
                                             String status,
                                     @ApiParam(name = "tags", value = "标签", required = false)
                                     @RequestParam(name = "tags", required = false)
                                             String tags,
                                     @ApiParam(name = "categories", value = "分类", required = false)
                                     @RequestParam(name = "categories", required = false, defaultValue = "默认分类")
                                             String categories,
                                     @ApiParam(name = "allowComment", value = "是否允许评论", required = true)
                                     @RequestParam(name = "allowComment", required = true)
                                             Boolean allowComment) {
        ContentDAO contentDAO = new ContentDAO();
        contentDAO.setCid(cid);
        contentDAO.setTitle(title);
        contentDAO.setTitlePic(titlePic);
        contentDAO.setSlug(slug);
        contentDAO.setContent(content);
        contentDAO.setType(type);
        contentDAO.setStatus(status);
        contentDAO.setTags(tags);
        contentDAO.setCategories(categories);
        contentDAO.setAllowComment(allowComment ? 1 : 0);
        contentService.updateArticleById(contentDAO);
        return APIResponse.success();
    }

    @GetMapping("/delete")
    @ResponseBody
    public APIResponse deleteArticle(@ApiParam(name = "cid", value = "文章主键", required = true)
                                     @RequestParam(name = "cid", required = true)
                                             Integer cid,
                                     HttpServletRequest request) {
        contentService.deleteArticleById(cid);
        logService.addLog(LogActions.DEL_ARTICLE.getAction(), cid + "", request.getRemoteAddr(), this.getUid(request));
        return APIResponse.success();
    }


}
