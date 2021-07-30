package com.nga.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//@Controller
//@RequestMapping(value = "error")
public class BaseErrorController  {
    private static final Logger LOGGER= LoggerFactory.getLogger(BaseErrorController.class);

    public String getErrorPath(){
        LOGGER.info("出错了！");
        return "error/error";
    }
    @RequestMapping
    public String error(){
        return getErrorPath();
    }
}
