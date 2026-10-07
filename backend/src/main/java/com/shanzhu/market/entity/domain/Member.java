package com.shanzhu.market.entity.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

@TableName("t_member")
public class Member implements Serializable {
    public static final String STATE_NORMAL = "0";
    public static final String STATE_BAN = "1";
    public static  final String DEFAULT_PWD="123456";

    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String phone;
    @JsonIgnore
    private String password;
    private String email;
    private Long integral;
    private String state;
    private String info;

    public Member() {
    }

    public Member(Long id, String name, String phone, String password, String email, Long integral, String state, String info) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.email = email;
        this.integral = integral;
        this.state = state;
        this.info = info;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getIntegral() {
        return integral;
    }

    public void setIntegral(Long integral) {
        this.integral = integral;
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
}
