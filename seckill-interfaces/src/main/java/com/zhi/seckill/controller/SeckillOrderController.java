package com.zhi.seckill.controller;

import com.zhi.seckill.application.service.SeckillOrderService;
import com.zhi.seckill.domain.code.HttpCode;
import com.zhi.seckill.domain.dto.SeckillOrderDTO;
import com.zhi.seckill.domain.model.SeckillOrder;
import com.zhi.seckill.domain.response.ResponseMessage;
import com.zhi.seckill.domain.response.ResponseMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ZhiJH
 * @description
 * @date 2024/5/21
 */
@RestController
@RequestMapping("/order")
public class SeckillOrderController {

    @Autowired
    private SeckillOrderService seckillOrderService;

    @RequestMapping(value = "/saveSeckillOrder", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseMessage<SeckillOrder> saveSeckillOrder(@RequestBody SeckillOrderDTO seckillOrderDTO) {
        SeckillOrder seckillOrder = seckillOrderService.saveSeckillOrder(seckillOrderDTO);
        return ResponseMessageBuilder.build(HttpCode.SUCCESS.getCode());
    }

    @RequestMapping(value = "/getSeckillOrderByUserId", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseMessage<List<SeckillOrder>> getSeckillOrderByUserId(Long userId) {
        List<SeckillOrder> seckillOrderList = seckillOrderService.getSeckillOrderByUserId(userId);
        return ResponseMessageBuilder.build(HttpCode.SUCCESS.getCode(), seckillOrderList);
    }

    @RequestMapping(value = "/getSeckillOrderByActivityId", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseMessage<List<SeckillOrder>> getSeckillOrderByActivityId(Long activityId) {
        List<SeckillOrder> seckillOrderList = seckillOrderService.getSeckillOrderByActivityId(activityId);
        return ResponseMessageBuilder.build(HttpCode.SUCCESS.getCode(), seckillOrderList);
    }
}
