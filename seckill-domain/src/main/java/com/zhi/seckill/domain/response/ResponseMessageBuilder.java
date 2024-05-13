package com.zhi.seckill.domain.response;

/**
 * @author ZhiJH
 * @description 响应消息构建类
 * @date 2024/5/11
 */
public class ResponseMessageBuilder {
    public static <T> ResponseMessage<T> build(Integer code, T data) {
        return new ResponseMessage<T>(code, data);
    }

    public static <T> ResponseMessage<T> build(Integer code) {
        return new ResponseMessage<T>(code);
    }
}
