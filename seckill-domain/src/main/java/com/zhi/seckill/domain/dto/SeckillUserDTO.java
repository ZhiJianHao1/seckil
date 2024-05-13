package com.zhi.seckill.domain.dto;

import java.io.Serializable;

/**
 * @author ZhiJH
 * @description 用户
 * @date 2024/5/12
 */
public class SeckillUserDTO implements Serializable {

    private static final long serialVersionUID = -2447612897028493326L;
    // 用户名
    private String userName;
    // 密码
    private String password;

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
}
