package com.nga.controller.admin;

import com.nga.dao.User;
import com.nga.service.impl.UserServiceImpl;
import com.nga.util.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminLoginController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/toLogin")
    public String toLogin(){

        return "admin/login";
    }

    @GetMapping({"/","/index","/index.html"})
    public String index(){
        return "admin/index";
    }


    @PostMapping("/login")
    public String login(@RequestParam("username")String username,@RequestParam("password") String password,Model model,HttpSession session){
        // 获取当前用户
        Subject subject = SecurityUtils.getSubject();
        // 将用户输入的用户名写密码封装到一个UsernamePasswordToken对象中
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // 用Subject对象执行登录方法
        try {
            subject.login(token);
            session.setAttribute("loginUser",username);
            model.addAttribute("username",username);
            return "admin/index";
        }catch (UnknownAccountException e){
            model.addAttribute("msg","用户名不存在");
            return "redirect:/admin/login";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误");
            return "redirect:/admin/login";
        }

    }

    @GetMapping("/desktop")
    public String desktop(){
        return "admin/my-desktop";
    }

    @PostMapping("/register")
    public ResultUtil register(User user){

        String msg = userService.addUser(user);
        if ("134".equals(msg)){
            return new ResultUtil(-1,false,"该用户名已被注册!");
        }else if ("135".equals(msg)){
            return new ResultUtil(-1,false,"该用户名已被注册!");
        }else if ("502".equals(msg)){
            return new ResultUtil(-1,false,"注册失败!");
        }else if ("200".equals(msg)){
            return new ResultUtil(-1,true,"注册成功!");
        }else {
            return new ResultUtil(-1,false,"未知错误!");
        }
    }
}
