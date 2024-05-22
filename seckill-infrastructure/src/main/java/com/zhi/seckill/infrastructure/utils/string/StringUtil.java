package com.zhi.seckill.infrastructure.utils.string;

/**
 * @author ZhiJH
 * @description 字符串工具类
 * @date 2024/5/22
 */
public class StringUtil {
    public static String append(Object ... params){
        if (params == null){
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < params.length - 1; i++){
            sb.append(params[i]).append("_");
        }
        sb.append(params[params.length - 1]);
        return sb.toString();
    }
}
