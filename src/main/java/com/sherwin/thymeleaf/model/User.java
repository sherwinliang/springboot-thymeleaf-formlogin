package com.sherwin.thymeleaf.model;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
/* @description: User model
 * @author: Sherwin Liang
 * @timestamp: 2021/2/27 16:37
 * @param:
 * @return:
*/
public class User {
    private Long id;

    private String username;

    private String password;

    private String mobile;

    private List<String> roles;

    public User() {
    }

    public User(Long id, String username, String password, String mobile, List<String> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.mobile = mobile;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<String> getRoles() {
        if(CollectionUtils.isEmpty(roles)){
            roles = new ArrayList<>();
        }
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
