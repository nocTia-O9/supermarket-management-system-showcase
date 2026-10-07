package com.shanzhu.market.entity.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@TableName("t_detail_store_goods")
public class DetailStoreGoods implements Serializable {
    public static final String STATE_NORMAL="0"; //正常
    public static final String STATE_EXPIRY="1"; //过期
    public static final String STATE_DOWN="2"; //下架
    public static final String STATE1_DEL="1"; //删除
    public static final String STATE1_NORMAL="0"; //正常
    public static final String STATE1_UNTREATED="2"; //待处理
    public static final String TYPE_IN="0";
    public static final String TYPE_OUT="1";
    private String cn;
    @TableField("goods_id")
    private Long goodsId;
    @TableField("goods_num")
    private Long goodsNum;
    @TableField("goods_name")
    private String goodsName;
    @TableField("goods_price")
    private Double goodsPrice;
    private String type;
    private Long createid;
    private String createby;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern ="yyyy-MM-dd" )
    @TableField("create_time")
    private Date createTime;
    private String state;
    private String info;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern ="yyyy-MM-dd" )
    @TableField("expiry_time")
    private Date expiryTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern ="yyyy-MM-dd" )
    @TableField("birth_time")
    private Date birthTime;
    @TableField("store_id")
    private Long storeId;
    private String state1;
    @TableField("supplier_id")
    private Long supplierId;
    @TableField("supplier_name")
    private String supplierName;
    @TableField("untreated_num")
    private Long untreatedNum;

    public DetailStoreGoods() {
    }

    public DetailStoreGoods(String cn, Long goodsId, Long goodsNum, String goodsName, Double goodsPrice, String type, Long createid, String createby, Date createTime, String state, String info, Date expiryTime, Date birthTime, Long storeId, String state1, Long supplierId, String supplierName, Long untreatedNum) {
        this.cn = cn;
        this.goodsId = goodsId;
        this.goodsNum = goodsNum;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.type = type;
        this.createid = createid;
        this.createby = createby;
        this.createTime = createTime;
        this.state = state;
        this.info = info;
        this.expiryTime = expiryTime;
        this.birthTime = birthTime;
        this.storeId = storeId;
        this.state1 = state1;
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.untreatedNum = untreatedNum;
    }

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

    public Long getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Long goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getCreateid() {
        return createid;
    }

    public void setCreateid(Long createid) {
        this.createid = createid;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Date getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
    }

    public Date getBirthTime() {
        return birthTime;
    }

    public void setBirthTime(Date birthTime) {
        this.birthTime = birthTime;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getState1() {
        return state1;
    }

    public void setState1(String state1) {
        this.state1 = state1;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Long getUntreatedNum() {
        return untreatedNum;
    }

    public void setUntreatedNum(Long untreatedNum) {
        this.untreatedNum = untreatedNum;
    }
}
