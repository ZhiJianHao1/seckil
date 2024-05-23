package com.zhi.seckill.application.common;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author ZhiJH
 * @description
 * @date 2024/5/23
 */
public class SeckillGoodsCommand implements Serializable {

    private static final long serialVersionUID = -256162830737171851L;
    //商品名称
    private String goodsName;
    //秒杀活动id
    private Long activityId;
    //商品原价
    private BigDecimal originalPrice;
    //秒杀活动价格
    private BigDecimal activityPrice;
    //初始库存
    private Integer initialStock;
    //限购个数
    private Integer limitNum;
    //描述
    private String description;
    //图片
    private String imgUrl;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getActivityPrice() {
        return activityPrice;
    }

    public void setActivityPrice(BigDecimal activityPrice) {
        this.activityPrice = activityPrice;
    }

    public Integer getInitialStock() {
        return initialStock;
    }

    public void setInitialStock(Integer initialStock) {
        this.initialStock = initialStock;
    }

    public Integer getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(Integer limitNum) {
        this.limitNum = limitNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
