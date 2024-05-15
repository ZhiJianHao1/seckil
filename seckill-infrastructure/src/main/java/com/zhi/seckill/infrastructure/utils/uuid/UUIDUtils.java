package com.zhi.seckill.infrastructure.utils.uuid;

import java.util.UUID;

/**
 * @author ZhiJH
 * @description UUID工具类
 * @date 2024/5/15
 */
public class UUIDUtils {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
