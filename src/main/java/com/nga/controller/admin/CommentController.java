package com.nga.controller.admin;

import com.github.pagehelper.PageInfo;
import com.nga.constan.ErrorConstant;
import com.nga.controller.BaseErrorController;
import com.nga.dao.CommentDAO;
import com.nga.dao.cond.CommentCond;
import com.nga.model.CommentModel;
import com.nga.model.UserModel;
import com.nga.service.CommentsService;
import com.nga.util.APIResponse;
import com.nga.util.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Api("评论相关接口")
@Controller
@RequestMapping("/admin/comment")
public class CommentController extends BaseErrorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentsService commentsService;

    @ApiOperation("进入评论页")
    @GetMapping("/index")
    public String index(
            @ApiParam(name = "page", value = "页数", required = false)
            @RequestParam(name = "page", required = false, defaultValue = "1")
                    int page,
            @ApiParam(name = "limit", value = "每页条数", required = false)
            @RequestParam(name = "limit", required = false, defaultValue = "15")
                    int limit,
            HttpServletRequest request
    ) {
        UserModel user = this.user(request);
        PageInfo<CommentModel> comment = commentsService.getCommentByCond(new CommentCond(), page, limit);
        request.setAttribute("comment", comment);
        return "admin/comment-management";
    }

    @ApiOperation("删除一条评论")
    @PostMapping("/delete")
    @ResponseBody
    public APIResponse deleteComment(
            @ApiParam(name = "coid", value = "评论编号", required = true)
            @RequestParam(name = "coid", required = true)
                    Integer coid
    ) {
        try {
            CommentModel comment = commentsService.getCommentById(coid);
            if (comment == null)
                throw BusinessException.withErrorCode(ErrorConstant.Comment.COMMENT_NOT_EXIST);
            commentsService.deleteComment(coid);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return APIResponse.fail(e.getMessage());
        }
        return APIResponse.success();
    }

    @ApiOperation("更改评论状态")
    @PostMapping("/status")
    @ResponseBody
    public APIResponse changeStatus(
            @ApiParam(name = "coid", value = "评论主键", required = true)
            @RequestParam(name = "coid", required = true)
                    Integer coid,
            @ApiParam(name = "status", value = "状态", required = true)
            @RequestParam(name = "status", required = true)
                    String status
    ) {
        try {
            CommentModel comment = commentsService.getCommentById(coid);
            if (comment != null) {
                commentsService.updateCommentStatus(coid, status);
            } else {
                return APIResponse.fail("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return APIResponse.fail(e.getMessage());
        }
        return APIResponse.success();
    }
}
