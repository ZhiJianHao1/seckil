package com.zhi.seckill.domain.model;

import java.io.Serializable;

/**
 * @author ZhiJH
 * @description 用户信息
 * @date 2024/5/11
 */
public class SeckillUser implements Serializable {

    private static final long serialVersionUID = 1184630517356586759L;
    // 用户Id
    private Long id;
    // 用户名称
    private String userName;
    // 用户密码
    private String password;
    // 1：正常 2：冻结
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
