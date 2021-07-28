package com.nga.config;

import com.nga.dao.User;
import com.nga.service.impl.UserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserServiceImpl userService;
    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        // 拿到当前登录对象
        User currentUser = (User) subject.getPrincipal();
        info.addStringPermission(currentUser.getPerms());

        return info;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        // 连接数据库
        User user = userService.queryUserByName(userToken.getUsername());
        if (user==null){
            return null;
        }

        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        session.setAttribute("loginUser",session);

        // 获取密码
        String credentials = user.getPassword();
        // 设置盐值，使用用户名
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getUsername());
        // 密码认证shiro做
        return new SimpleAuthenticationInfo("",user.getPassword(),"");// 未加密
//        return new SimpleAuthenticationInfo(user,credentials,credentialsSalt,user.getUsername());// 返回已加密的密码
    }
}
