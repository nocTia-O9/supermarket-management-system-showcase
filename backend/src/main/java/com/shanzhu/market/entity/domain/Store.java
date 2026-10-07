package com.shanzhu.market.entity.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 仓库实体
 */
@TableName("store")
public class Store  implements Serializable {
    public static  final String  STATE_NORMAL="0";
    public static  final String  STATE_BAN="-1";
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String address;
    private String info;
    private String state;

    public Store() {
    }

    public Store(Long id, String name, String address, String info, String state) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.info = info;
        this.state = state;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
