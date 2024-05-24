package com.zhi.seckill.domain.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ZhiJH
 * @description 活动信息
 * @date 2024/5/11
 */
public class SeckillActivity implements Serializable {
    private static final long serialVersionUID = -3859403735063141008L;
    // 活动id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    // 活动名称
    private String activityName;
    // 活动开始时间
    private Date startTime;
    // 活动结束时间
    private Date endTime;
    // 活动状态：0 已发布 1：上线 2：下线
    private Integer status;
    // 活动描述
    private String activityDesc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getActivityDesc() {
        return activityDesc;
    }

    public void setActivityDesc(String activityDesc) {
        this.activityDesc = activityDesc;
    }

    public boolean validateParams(){
        if (StringUtils.isEmpty(activityDesc)
                || startTime == null
                || endTime == null
                || endTime.before(startTime)
                || endTime.before(new Date())){
            return false;
        }
        return true;
    }
}
