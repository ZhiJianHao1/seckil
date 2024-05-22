package com.zhi.seckill.controller;

import com.zhi.seckill.application.service.SeckillUserService;
import com.zhi.seckill.domain.code.HttpCode;
import com.zhi.seckill.domain.model.dto.SeckillUserDTO;
import com.zhi.seckill.domain.model.entity.SeckillUser;
import com.zhi.seckill.domain.response.ResponseMessage;
import com.zhi.seckill.domain.response.ResponseMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ZhiJH
 * @description SeckillUserController
 * @date 2024/5/12
 */
@RestController
@RequestMapping(value = "/user")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*", originPatterns = "*")
public class SeckillUserController {

    @Autowired
    private SeckillUserService seckillUserService;

    /**
     * 测试系统
     */
    @RequestMapping(value = "/get", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseMessage<SeckillUser> getUser(@RequestAttribute Long userId) {
        return ResponseMessageBuilder.build(HttpCode.SUCCESS.getCode(), seckillUserService.getSeckillUserByUserId(userId));
    }

    /**
     * 登录系统
     */
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseMessage<String> login(@RequestBody SeckillUserDTO seckillUserDTO) {
        return ResponseMessageBuilder.build(HttpCode.SUCCESS.getCode(), seckillUserService.login(seckillUserDTO.getUserName(), seckillUserDTO.getPassword()));
    }
}
