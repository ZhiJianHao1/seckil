package com.zhi.seckill.application.cache.model.common;

/**
 * @author ZhiJH
 * @description 通用缓存模型
 * @date 2024/5/22
 */
public class SeckillCommonCache {
    // 缓存数据是否存在
    protected boolean exist;
    // 缓存版本号
    protected Long version;
    // 稍后在试
    protected boolean retryLater;

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public boolean isRetryLater() {
        return retryLater;
    }

    public void setRetryLater(boolean retryLater) {
        this.retryLater = retryLater;
    }
}
