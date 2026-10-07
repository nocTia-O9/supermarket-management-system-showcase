package com.shanzhu.market.entity.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@TableName("t_sale_records")
public class SaleRecords implements Serializable {
    public static final String STATE_NORMAL="0";
    public static final String STATE_DEL="1";
    private static final String  TYPE_MEMBER="1";
    private static final String TYPE_NOMEMBER="0";
    private static final String SELLWAY_ALIPAY="0";
    private static final String SELLWAY_WECHAT="1";
    private static final String SELLWAY_CASH="2";
    private static final String SELLWAY_CREDIT="3";
    private static final Double DISCOUNT=0.9;
    @TableField("cn")
    private String cn;
    private Long eid;
    private String sellway;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("sell_time")
    private Date sellTime;
    private String state;
    private String info;
    private String sellby;
    @TableField("sell_total")
    private Long sellTotal;
    @TableField("sell_totalmoney")
    private Double sellTotalmoney;
    private String type;
    @TableField(exist = false)
    private List<DetailSaleRecords> detailSaleRecords=new ArrayList<>();
    @TableField("member_phone")
    private String memberPhone;//会员账号

    public SaleRecords() {
    }

    public SaleRecords(String cn, Long eid, String sellway, Date sellTime, String state, String info, String sellby, Long sellTotal, Double sellTotalmoney, String type, List<DetailSaleRecords> detailSaleRecords, String memberPhone) {
        this.cn = cn;
        this.eid = eid;
        this.sellway = sellway;
        this.sellTime = sellTime;
        this.state = state;
        this.info = info;
        this.sellby = sellby;
        this.sellTotal = sellTotal;
        this.sellTotalmoney = sellTotalmoney;
        this.type = type;
        this.detailSaleRecords = detailSaleRecords;
        this.memberPhone = memberPhone;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public Long getEid() {
        return eid;
    }

    public void setEid(Long eid) {
        this.eid = eid;
    }

    public String getSellway() {
        return sellway;
    }

    public void setSellway(String sellway) {
        this.sellway = sellway;
    }

    public Date getSellTime() {
        return sellTime;
    }

    public void setSellTime(Date sellTime) {
        this.sellTime = sellTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getSellby() {
        return sellby;
    }

    public void setSellby(String sellby) {
        this.sellby = sellby;
    }

    public Long getSellTotal() {
        return sellTotal;
    }

    public void setSellTotal(Long sellTotal) {
        this.sellTotal = sellTotal;
    }

    public Double getSellTotalmoney() {
        return sellTotalmoney;
    }

    public void setSellTotalmoney(Double sellTotalmoney) {
        this.sellTotalmoney = sellTotalmoney;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<DetailSaleRecords> getDetailSaleRecords() {
        return detailSaleRecords;
    }

    public void setDetailSaleRecords(List<DetailSaleRecords> detailSaleRecords) {
        this.detailSaleRecords = detailSaleRecords;
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }
}
