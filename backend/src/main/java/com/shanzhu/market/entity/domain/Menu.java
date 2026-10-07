package com.shanzhu.market.entity.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@TableName("t_menu")
public class Menu implements Serializable {
    public static final String TYPE_CATALOGUE="0";//目录
    public static final String TYPE_MENU="1"; //菜单
    public static final String TYPE_BUTTON="2";//按钮
    public static final String STATE_NORMAL="0";//正常
    public static final String STATE_DEL="-1";//禁用
    @TableId(type = IdType.AUTO)
    private Long id;
    private String label;
    private String purl;
    private String type;
    @TableField("parent_id")
    private Long parentId;
    @TableField("parent_label")
    private String parentLabel;
    private String info;
    private String state;
    private String flag;
    private String icon;
    private String component;
    @TableField(exist = false)
    private List<Menu> children;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Objects.equals(id, menu.id) &&
                Objects.equals(label, menu.label) &&
                Objects.equals(purl, menu.purl) &&
                Objects.equals(type, menu.type) &&
                Objects.equals(parentId, menu.parentId) &&
                Objects.equals(parentLabel, menu.parentLabel) &&
                Objects.equals(info, menu.info) &&
                Objects.equals(state, menu.state) &&
                Objects.equals(flag, menu.flag) &&
                Objects.equals(component, menu.component) &&
                Objects.equals(children, menu.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label, purl, type, parentId, parentLabel, info, state, flag, children);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentLabel() {
        return parentLabel;
    }

    public void setParentLabel(String parentLabel) {
        this.parentLabel = parentLabel;
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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }
}
