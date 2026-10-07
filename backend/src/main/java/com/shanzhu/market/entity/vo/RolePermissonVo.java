package com.shanzhu.market.entity.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

import java.io.Serializable;
import java.util.List;

public class RolePermissonVo implements Serializable {
    private List<Long> menuIds;
    private List<RoleMenu> menus;

    /**
     * 获取成员内部类的实例
     *
     * @return
     */
    public RoleMenu getRoleMenu() {
        return new RoleMenu();
    }

    public List<Long> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<Long> menuIds) {
        this.menuIds = menuIds;
    }

    public List<RoleMenu> getMenus() {
        return menus;
    }

    public void setMenus(List<RoleMenu> menus) {
        this.menus = menus;
    }

    /**
     * 成员内部类，供这个类使用
     */
    @JsonIgnoreType
    public class RoleMenu {
        private Long value;
        private String label;
        private List<RoleMenu> children;

        public Long getValue() {
            return value;
        }

        public void setValue(Long value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public List<RoleMenu> getChildren() {
            return children;
        }

        public void setChildren(List<RoleMenu> children) {
            this.children = children;
        }
    }

}
