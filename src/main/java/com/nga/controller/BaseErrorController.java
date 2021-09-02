package com.nga.controller;

import com.nga.constan.WebConst;
import com.nga.dao.MetaUtil;
import com.nga.dao.UserDAO;
import com.nga.dao.cond.ContentCond;
import com.nga.model.UserModel;
import com.nga.service.ContentService;
import com.nga.service.MetaService;
import com.nga.util.MapCache;
import com.nga.util.TaleUtils;
import com.nga.util.TypesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

//@Controller
//@RequestMapping(value = "error")
public abstract class BaseErrorController {
    @Autowired
    private ContentService contentService;
    @Autowired
    private MetaService metaService;

    protected MapCache cache = MapCache.single();

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseErrorController.class);

    public BaseErrorController title(HttpServletRequest request, String title) {
        request.setAttribute("title", title);
        return this;
    }

    /**
     * 获取blog页面需要的公共数据
     *
     * @param request
     * @param contentCond
     * @return
     */
    public BaseErrorController blogBaseData(HttpServletRequest request, ContentCond contentCond) {
        List<MetaUtil> links = metaService.getMetaList(TypesUtil.LINK.getType(), null, WebConst.MAX_POSTS);
        request.setAttribute("links", links);
        return this;
    }

    /**
     * 获取请求绑定的登录对象
     *
     * @param request
     * @return
     */
    public UserModel user(HttpServletRequest request) {
        return TaleUtils.getLoginUser(request);
    }
    public Integer getUid(HttpServletRequest request){
        return this.user(request).getUid();
    }

    /**
     * 数组转换字符串
     * @param arr
     * @return
     */
    public String join(String[] arr) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] var3 = arr;
        for (int var5 = 0; var5 < arr.length; ++var5) {
            String item = var3[var5];
            stringBuilder.append(',').append(item);
        }
        return stringBuilder.length() > 0 ? stringBuilder.substring(1) : stringBuilder.toString();
    }

    public String getErrorPath() {
        LOGGER.info("出错了！");
        return "error/error";
    }

    @RequestMapping
    public String error() {
        return getErrorPath();
    }
}
