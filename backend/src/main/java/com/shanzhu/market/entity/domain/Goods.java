package com.shanzhu.market.entity.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品
 */
@TableName("goods")
public class Goods implements Serializable {
    public static final  String STATE_UP="0";
    public static final String STATE_DOWN="1";
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name; //商品名
    private String info;
    @TableField("category_name")
    private String categoryName; //商品分类名
    @TableField("category_id")
    private Long categoryId; //商品分类id
    private String createby; //创建者
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField("create_time")
    private Date createTime; //创建时间
    private String updateby; //更新者
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField("update_time")
    private Date updateTime; //更新时间
    @TableField("sell_price")
    private Double sellPrice; // 销售价格
    @TableField("purchash_price")
    private Double purchashPrice; //进货价格
    @TableField("residue_num")
    private Long residueNum; //剩余数量
    @TableField(exist = false)
    private Long residueStoreNum;//剩余库存数量
    @TableField("cover_url")
    private String coverUrl; //商品封面
    private String state=STATE_UP;
    @TableField("sales_volume")
    private Long salesVolume;//销售量
    private Long inventory;//需库存量
    private Long shelves;//货架商品数量

    public Goods() {
    }

    public Goods(Long id, String name, String info, String categoryName, Long categoryId, String createby, Date createTime, String updateby, Date updateTime, Double sellPrice, Double purchashPrice, Long residueNum, Long residueStoreNum, String coverUrl, String state, Long salesVolume, Long inventory, Long shelves) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        this.createby = createby;
        this.createTime = createTime;
        this.updateby = updateby;
        this.updateTime = updateTime;
        this.sellPrice = sellPrice;
        this.purchashPrice = purchashPrice;
        this.residueNum = residueNum;
        this.residueStoreNum = residueStoreNum;
        this.coverUrl = coverUrl;
        this.state = state;
        this.salesVolume = salesVolume;
        this.inventory = inventory;
        this.shelves = shelves;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Double getPurchashPrice() {
        return purchashPrice;
    }

    public void setPurchashPrice(Double purchashPrice) {
        this.purchashPrice = purchashPrice;
    }

    public Long getResidueNum() {
        return residueNum;
    }

    public void setResidueNum(Long residueNum) {
        this.residueNum = residueNum;
    }

    public Long getResidueStoreNum() {
        return residueStoreNum;
    }

    public void setResidueStoreNum(Long residueStoreNum) {
        this.residueStoreNum = residueStoreNum;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(Long salesVolume) {
        this.salesVolume = salesVolume;
    }

    public Long getInventory() {
        return inventory;
    }

    public void setInventory(Long inventory) {
        this.inventory = inventory;
    }

    public Long getShelves() {
        return shelves;
    }

    public void setShelves(Long shelves) {
        this.shelves = shelves;
    }
}
