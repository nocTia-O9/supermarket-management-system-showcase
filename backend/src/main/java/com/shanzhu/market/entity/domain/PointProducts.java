package com.shanzhu.market.entity.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
@TableName("point_products")
public class PointProducts implements Serializable {
    public static  final  String STATE_NORMAL="0";
    public static  final  String STATE_DEL="1";
    @TableField("goods_id")
    private Long goodsId;
    @TableField("goods_name")
    private String goodsName;
    private Long integral;
    private String updateby;
    @TableField("update_time")
    private Date updateTime;
    @TableField("cover_url")
    private String coverUrl;
    @TableField("update_id")
    private Long updateId;
    private String state;

    public PointProducts() {
    }

    public PointProducts(Long goodsId, String goodsName, Long integral, String updateby, Date updateTime, String coverUrl, Long updateId, String state) {
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.integral = integral;
        this.updateby = updateby;
        this.updateTime = updateTime;
        this.coverUrl = coverUrl;
        this.updateId = updateId;
        this.state = state;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Long getIntegral() {
        return integral;
    }

    public void setIntegral(Long integral) {
        this.integral = integral;
    }

    public String getUpdateby() {
        return updateby;
    }

    public void setUpdateby(String updateby) {
        this.updateby = updateby;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
