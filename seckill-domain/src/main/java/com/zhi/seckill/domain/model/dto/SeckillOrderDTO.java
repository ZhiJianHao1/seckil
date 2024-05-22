package com.zhi.seckill.domain.model.dto;

import java.io.Serializable;

/**
 * @author ZhiJH
 * @description 订单DTO
 * @date 2024/5/19
 */
public class SeckillOrderDTO implements Serializable {

    private static final long serialVersionUID = 2628736795048923680L;

    // 订单Id
    private Long id;
    // 用户Id
    private Long userId;
    // 商品Id
    private Long goodsId;
    // 购买数量
    private Integer quantity;
    // 活动id
    private Long activityId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }
}
