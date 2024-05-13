package com.zhi.seckill.domain.exception;

import com.zhi.seckill.domain.code.HttpCode;

/**
 * @author ZhiJH
 * @description 自定义异常
 * @date 2024/5/12
 */
public class SeckillException extends RuntimeException {
    private static final long serialVersionUID = 8684193147532893184L;

    private Integer code;

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(HttpCode errorCode) {
        this(errorCode.getCode(), errorCode.getMessage());
    }

    public SeckillException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
