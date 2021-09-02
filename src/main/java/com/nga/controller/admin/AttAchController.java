package com.nga.controller.admin;

import com.github.pagehelper.PageInfo;
import com.nga.constan.ErrorConstant;
import com.nga.constan.WebConst;
import com.nga.dao.AttAchDAO;
import com.nga.model.AttAchModel;
import com.nga.model.UserModel;
import com.nga.service.AttAchService;
import com.nga.util.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("admin/attach")
public class AttAchController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AttAchController.class);
    public static final String CLASSPATH = TaleUtils.getUploadFilePath();
    @Autowired
    private AttAchService attAchService;

    /**
     * 文件管理首页
     *
     * @param page
     * @param limit
     * @param request
     * @return
     */
    @GetMapping("/index")
    public String index(
            @ApiParam(name = "page", value = "页数", required = false)
            @RequestParam(name = "page", required = false, defaultValue = "1")
                    int page,
            @ApiParam(name = "limit", value = "条数", required = false)
            @RequestParam(name = "limit", required = false, defaultValue = "12")
                    int limit,
            HttpServletRequest request
    ) {
        PageInfo<AttAchDAO> atts = attAchService.getAtts(page, limit);
        request.setAttribute("attachs", atts);
        request.setAttribute(TypesUtil.ATTACH_URL.getType(), Commons.site_option(TypesUtil.ATTACH_URL.getType(), Commons.site_url()));
        request.setAttribute("max_file_size", WebConst.MAX_FILE_SIZE / 1024);
        return "";
    }

    /**
     * Markdown文件上传
     *
     * @param request
     * @param response
     * @param file
     */
    @ApiOperation("Markdown文件上传")
    @PostMapping("/uploadfile")
    public void fileUploadTencentCloud(HttpServletRequest request,
                                       HttpServletResponse response,
                                       @ApiParam(name = "editormd-image-file", value = "文件数组", required = true)
                                       @RequestParam(name = "editormd-image-file", required = true)
                                               MultipartFile file) {
        try {
            request.setCharacterEncoding("utf-8");
            response.setHeader("Content-Type", "text/html");

            String fileName = TaleUtils.getFileKey(file.getOriginalFilename()).replaceFirst("/", "");

            AttAchModel attAchModel = new AttAchModel();
            HttpSession session = request.getSession();
            UserModel sessionUser = (UserModel) session.getAttribute(WebConst.LOGIN_SESSION_KEY);
            attAchModel.setAuthorId(sessionUser.getUid());
            attAchModel.setFtype(TaleUtils.isImage(file.getInputStream()) ? TypesUtil.IMAGE.getType() : TypesUtil.FILE.getType());
            attAchModel.setFname(fileName);
            attAchService.addAttAch(attAchModel);
            response.getWriter().write("{\"success\":1,\"message\":\"上传成功\",url\":\"" + attAchModel.getFkey() + "\"}");
        } catch (IOException e) {
            e.printStackTrace();
            try {
                response.getWriter().write("{\"success\":0}");
            } catch (IOException ex) {
                throw BusinessException.withErrorCode(ErrorConstant.Att.UPLOAD_FILE_FAIL)
                        .withErrorMessageArguments(e.getMessage());
            }
            throw BusinessException.withErrorCode(ErrorConstant.Att.UPLOAD_FILE_FAIL)
                    .withErrorMessageArguments(e.getMessage());
        }
    }

    /**
     * 多文件上传
     *
     * @param request
     * @param response
     * @param files
     * @return
     */
    @ApiOperation("多文件上传")
    @PostMapping("/upload")
    @ResponseBody
    public APIResponse fileUploadToCloud(HttpServletRequest request,
                                         HttpServletResponse response,
                                         @ApiParam(name = "file", value = "文件数组", required = true)
                                         @RequestParam(name = "file", required = true)
                                                 MultipartFile[] files) {
        // 文件上传
        try {
            request.setCharacterEncoding("utf-8");
            response.setHeader("Content-Type", "text/html");

            for (MultipartFile file : files) {
                String fileName = TaleUtils.getFileKey(file.getOriginalFilename()).replaceFirst("/", "");

                AttAchModel attAchModel = new AttAchModel();
                HttpSession session = request.getSession();
                UserModel sessionUser = (UserModel) session.getAttribute(WebConst.LOGIN_SESSION_KEY);
                attAchModel.setAuthorId(sessionUser.getUid());
                attAchModel.setFtype(TaleUtils.isImage(file.getInputStream()) ? TypesUtil.IMAGE.getType() : TypesUtil.FILE.getType());
                attAchModel.setFname(fileName);
                attAchService.addAttAch(attAchModel);
            }
            return APIResponse.success();
        } catch (IOException e) {
            e.printStackTrace();
            throw BusinessException.withErrorCode(ErrorConstant.Att.UPLOAD_FILE_FAIL)
                    .withErrorMessageArguments(e.getMessage());
        }
    }

    @ApiOperation("删除文件记录")
    @PostMapping("/delete")
    @ResponseBody
    public APIResponse deleteFileInfo(
            @ApiParam(name = "id", value = "文件主键", required = true)
            @RequestParam(name = "id", required = true)
                    Integer id,
            HttpServletRequest request
    ) {
        try {
            AttAchDAO attAchById = attAchService.getAttAchById(id);
            if (attAchById == null)
                throw BusinessException.withErrorCode(ErrorConstant.Att.DELETE_ATT_FAIL + ": 文件不存在");
            attAchService.deleteAttAch(id);
            return APIResponse.success();

        } catch (Exception e) {
            e.printStackTrace();
            throw BusinessException.withErrorCode(e.getMessage());
        }
    }
}
