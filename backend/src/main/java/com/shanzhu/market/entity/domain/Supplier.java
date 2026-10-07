package com.shanzhu.market.entity.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName("supplier")
public class Supplier implements Serializable {
    public static  final String  STATE_NORMAL="0";
    public static  final String  STATE_BAN="-1";
    @TableId(type = IdType.AUTO)
    private Long cn;
    private String name;
    private String address;
    private String tel;
    private String info;
    private String state;

    public Supplier() {
    }

    public Supplier(Long cn, String name, String address, String tel, String info, String state) {
        this.cn = cn;
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.info = info;
        this.state = state;
    }

    public Long getCn() {
        return cn;
    }

    public void setCn(Long cn) {
        this.cn = cn;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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
