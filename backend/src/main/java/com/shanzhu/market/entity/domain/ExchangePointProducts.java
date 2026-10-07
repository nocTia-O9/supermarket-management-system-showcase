package com.shanzhu.market.entity.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

@TableName("exchange_point_products_records")
public class ExchangePointProducts implements Serializable {
    public static  final  String STATE_NORMAL="0";
    public static  final  String STATE_DEL="1";
    private String cn;
    @TableField("goods_id")
    private Long goodsId;
    @TableField("member_id")
    private Long memberId;
    private Long integral;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("update_time")
    private Date updateTime;
    private String updateby;
    @TableField("update_id")
    private Long updateId;
    private String state;
    @TableField(exist = false)
    private String memberPhone;//会员账号
    @TableField(exist = false)
    private String goodsName;
    @TableField(exist = false)
    private String goodsCoverUrl;

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getIntegral() {
        return integral;
    }

    public void setIntegral(Long integral) {
        this.integral = integral;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateby() {
        return updateby;
    }

    public void setUpdateby(String updateby) {
        this.updateby = updateby;
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

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsCoverUrl() {
        return goodsCoverUrl;
    }

    public void setGoodsCoverUrl(String goodsCoverUrl) {
        this.goodsCoverUrl = goodsCoverUrl;
    }
}
