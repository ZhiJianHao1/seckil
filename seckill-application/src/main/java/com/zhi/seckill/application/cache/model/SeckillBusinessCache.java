package com.zhi.seckill.application.cache.model;

import com.zhi.seckill.application.cache.model.common.SeckillCommonCache;

/**
 * @author ZhiJH
 * @description 业务数据缓存
 * @date 2024/5/22
 */
public class SeckillBusinessCache<T> extends SeckillCommonCache {
    private T data;

    public SeckillBusinessCache<T> with(T data){
        this.data = data;
        this.exist = true;
        return this;
    }

    public SeckillBusinessCache<T> withVersion(Long version){
        this.version = version;
        return this;
    }

    public SeckillBusinessCache<T> retryLater(){
        this.retryLater = true;
        return this;
    }

    public SeckillBusinessCache<T> notExist(){
        this.exist = false;
        return this;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
