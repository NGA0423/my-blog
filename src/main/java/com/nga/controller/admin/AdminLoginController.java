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

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
            return "admin/login";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误");
            return "admin/login";
        }

    }

    @GetMapping("/desktop")
    public String desktop(Model model,HttpSession session){
        Date date = new Date();
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        model.addAttribute("nowDate",format);

        Object username = session.getAttribute("loginUser");
        model.addAttribute("username",username);

        return "admin/my-desktop";
    }

    @PostMapping("/register")
    @ResponseBody
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

//    @GetMapping("/logout")
//    public void logout(HttpSession session, HttpServletResponse response) {
//
//        try {
//            response.sendRedirect("/admin/toLogin");
//            session.invalidate();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response) {
        session.removeAttribute("loginUser");
        return "redirect:/toLogin";
    }

}
