package com.nga.config;

import com.nga.dao.User;
import com.nga.service.UserService;
import com.nga.service.impl.UserServiceImpl;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserServiceImpl userService;
    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken userToken= (UsernamePasswordToken) token;
        User user = userService.queryUserByName(userToken.getUsername());
        // 当前realm对象的name
        String realmName = getName();
        // 盐值
        ByteSource credentialsSalt=null;
        if (user==null){
            // 抛出异常
            return null;
        }else {
            credentialsSalt=ByteSource.Util.bytes(user.getUsername());
        }
        return new SimpleAuthenticationInfo(user,user.getPassword(),credentialsSalt,realmName);
    }
}
