package com.nga.model;

public class UserModel {
    /** 主键编号 */
    private Integer uid;
    /** 用户名 */
    private String username;
    /** 密码 */
    private String password;
    /** email */
    private String email;
    /** 主页地址 */
    private String homeUrl ;
    /** 显示用户的名称 */
    private String screenName ;
    /** 用户注册时间戳的GMT unix时间戳 */
    private Integer created;
    /** 最后活动时间 */
    private Integer activated;
    /** 上传登录最后活跃时间 */
    private Integer logged;
    /** 用户组 */
    private Integer groupName;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomeUrl() {
        return homeUrl;
    }

    public void setHomeUrl(String homeUrl) {
        this.homeUrl = homeUrl;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }

    public Integer getActivated() {
        return activated;
    }

    public void setActivated(Integer activated) {
        this.activated = activated;
    }

    public Integer getLogged() {
        return logged;
    }

    public void setLogged(Integer logged) {
        this.logged = logged;
    }

    public Integer getGroupName() {
        return groupName;
    }

    public void setGroupName(Integer groupName) {
        this.groupName = groupName;
    }
}
