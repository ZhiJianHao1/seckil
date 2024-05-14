package com.zhi.seckill.interceptor;

import com.zhi.seckill.domain.code.HttpCode;
import com.zhi.seckill.domain.constants.SeckillConstants;
import com.zhi.seckill.domain.exception.SeckillException;
import com.zhi.seckill.infrastructure.shiro.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ZhiJH
 * @description 登录授权拦截验证
 * @date 2024/5/13
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final String USER_ID = "userId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object userIdObj = request.getAttribute(USER_ID);
        if (userIdObj != null){
            return true;
        }
        String token = request.getHeader(SeckillConstants.TOKEN_HEADER_NAME);
        if (StringUtils.isEmpty(token)) {
            throw new SeckillException(HttpCode.USER_NOT_LOGIN);
        }
        Long userId = JwtUtils.getUserId(token);
        if (userId == null) {
            throw new SeckillException(HttpCode.USER_NOT_LOGIN);
        }
        HttpServletRequestWrapper authRequestWrapper = new HttpServletRequestWrapper(request);
        authRequestWrapper.setAttribute(USER_ID, userId);
        return true;
    }
}
