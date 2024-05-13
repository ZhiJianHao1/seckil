package com.zhi.seckill.domain.constants;

/**
 * @author ZhiJH
 * @description 秒杀常量类
 * @date 2024/5/12
 */
public class SeckillConstants {
    /**
     * 用户缓存前缀
     */
    public static final String USER_KEY_PREFIX = "user_";

    /**
     * 获取key
     */
    public static String getKey(String prefix, String key) {
        return prefix.concat(key);
    }

    /**
     * token的载荷中存放的信息 只存放一个userId
     */
    public static final String TOKEN_CLAIM = "userId";
    /**
     * jwtToken过期时间 默认为7天
     */
    public static final Long TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L;
    /**
     * token请求头名称
     */
    public static final String TOKEN_HEADER_NAME = "access-token";

    public static final String JWT_SECRET = "a814edb0e7c1ba4c";
}
