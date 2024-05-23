package com.zhi.seckill.controller;

import com.zhi.seckill.application.common.SeckillGoodsCommand;
import com.zhi.seckill.application.service.SeckillGoodsService;
import com.zhi.seckill.domain.code.HttpCode;
import com.zhi.seckill.domain.model.dto.SeckillGoodsDTO;
import com.zhi.seckill.domain.model.entity.SeckillGoods;
import com.zhi.seckill.domain.response.ResponseMessage;
import com.zhi.seckill.domain.response.ResponseMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ZhiJH
 * @description 商品接口
 * @date 2024/5/18
 */
@RestController
@RequestMapping(value = "/goods")
public class SeckillGoodsController {

    @Autowired
    private SeckillGoodsService seckillGoodsService;

    /**
     * 保存秒杀商品
     */
    @RequestMapping(value = "/saveSeckillGoods", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseMessage<String> saveSeckillActivityDTO(SeckillGoodsCommand seckillGoodsCommand) {
        seckillGoodsService.saveSeckillGoods(seckillGoodsCommand);
        return ResponseMessageBuilder.build(HttpCode.SUCCESS.getCode());
    }

    /**
     * 获取商品详情
     */
    @RequestMapping(value = "/getSeckillGoodsId", method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseMessage<SeckillGoods> getSeckillGoodsId(Long id){
        return ResponseMessageBuilder.build(HttpCode.SUCCESS.getCode(), seckillGoodsService.getSeckillGoodsId(id));
    }

    /**
     * 获取商品列表
     */
    @RequestMapping(value = "/getSeckillGoodsByActivityId", method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseMessage<List<SeckillGoods>> getSeckillGoodsByActivityId(Long activityId){
        return ResponseMessageBuilder.build(HttpCode.SUCCESS.getCode(), seckillGoodsService.getSeckillGoodsByActivityId(activityId));
    }

    /**
     * 更新商品状态
     */
    @RequestMapping(value = "/updateStatus", method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseMessage<String> updateStatus(Integer status, Long id){
        seckillGoodsService.updateStatus(status, id);
        return ResponseMessageBuilder.build(HttpCode.SUCCESS.getCode());
    }

    /**
     * 获取商品列表(带缓存)
     */
    @RequestMapping(value = "/getSeckillGoodsList", method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseMessage<List<SeckillGoodsDTO>> getSeckillGoodsByActivityId(Long activityId, Long version){
        return ResponseMessageBuilder.build(HttpCode.SUCCESS.getCode(), seckillGoodsService.getSeckillGoodsList(activityId, version));
    }
}
