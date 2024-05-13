package com.zhi.seckill.infrastructure.shiro.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zhi.seckill.domain.constants.SeckillConstants;

import java.util.Date;

/**
 * @author ZhiJH
 * @description
 * @date 2024/5/13
 */
public class JwtUtils {

    /**
     * 校验token是否正确
     * @param token 密钥
     * @return 是否正确
     */
    public static boolean verify(String token, String secret) {
        try {
            // 根据校验器生成密钥
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim(SeckillConstants.TOKEN_CLAIM, getUserId(token))
                    .build();
            // 校验token
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户名
     */
    public static Long getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(SeckillConstants.TOKEN_CLAIM).asLong();
        } catch (JWTDecodeException exception) {
            return null;
        }
    }


    /**
     *
     * @param userId 用户名
     * @param secret 制作token的签名依据
     * @return 加密的token
     */
    public static String sign(Long userId, String secret) {
        Date date = new Date(System.currentTimeMillis() + SeckillConstants.TOKEN_EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withClaim(SeckillConstants.TOKEN_CLAIM, userId)
                .withExpiresAt(date)
                .sign(algorithm);
    }

    /**
     *
     * @param userId 用户名
     * @return 加密token
     */
    public static String sign(Long userId) {
        return sign(userId, SeckillConstants.JWT_SECRET);
    }
}
