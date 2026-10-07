package com.shanzhu.market.entity.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 员工实体类
 */
@TableName("employee")
public class Employee implements Serializable {
    public static final String STATE_NORMAL = "0";
    public static final String STATE_DEL = "1";
    public static final String DEFAULT_PWD = "123456";
    public static final String SEX_MEN = "1";
    public static final String SEX_WOWEN = "0";
    public static final String DEFAULT_HEAD_IMG="/files/1694434162457_07.jpg";
    @TableId(type = IdType.AUTO)
    private Long id;
    private String sex;
    @TableField("isAdmin")
    private Boolean isAdmin;
    @TableField("phone")
    private String username;
    @TableField("nick_name")
    private String nickName;
    private String password;
    @TableField("head_img")
    private String headImg=DEFAULT_HEAD_IMG;
    private String state = STATE_NORMAL;
    private String info;
    private String createby;
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField("leave_time")
    private Date leaveTime;
    private String address;
    private String email;
    private Integer age;
    @TableField("deptId")
    private Long deptId;

    @TableField(value = "dept_name",exist = false)
    private String deptName;
    //角色集合
    @TableField(exist = false)
    @JsonIgnore
    private Set<Role> roles;

    //权限集合
    @TableField(exist = false)
    @JsonIgnore
    private List<Menu> menus;
    @TableField(exist = false)
    @JsonIgnore
    private Set<String> flags;

    @TableField("id_card")
    private String idCard;

    public Employee() {
    }

    public Employee(Long id, String sex, Boolean isAdmin, String username, String nickName, String password, String headImg, String state, String info, String createby, Date createTime, Date leaveTime, String address, String email, Integer age, Long deptId, String deptName, Set<Role> roles, List<Menu> menus, Set<String> flags, String idCard) {
        this.id = id;
        this.sex = sex;
        this.isAdmin = isAdmin;
        this.username = username;
        this.nickName = nickName;
        this.password = password;
        this.headImg = headImg;
        this.state = state;
        this.info = info;
        this.createby = createby;
        this.createTime = createTime;
        this.leaveTime = leaveTime;
        this.address = address;
        this.email = email;
        this.age = age;
        this.deptId = deptId;
        this.deptName = deptName;
        this.roles = roles;
        this.menus = menus;
        this.flags = flags;
        this.idCard = idCard;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
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

    public Date getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Date leaveTime) {
        this.leaveTime = leaveTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public Set<String> getFlags() {
        return flags;
    }

    public void setFlags(Set<String> flags) {
        this.flags = flags;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
        if (menus!=null) {
            this.flags = getFlags(this.menus);
        }
    }

    /**
     * 获取权限标识符集合
     *
     * @param menus
     * @return
     */
    private Set<String> getFlags(List<Menu> menus) {
        Set<String> flags = new HashSet<>();
        for (Menu menu : menus) {
            //目录遍历
            if (menu.getFlag() != null) {
                flags.add(menu.getFlag());
                //如果没有子集
                if (menu.getChildren() == null) {
                    continue;
                }
                for (Menu child : menu.getChildren()) {
                    //菜单遍历
                    if (child.getFlag() != null) {
                        flags.add(child.getFlag());
                    }
                    //如果没有子集
                    if (child.getChildren() == null) {
                        continue;
                    }
                    for (Menu childChild : child.getChildren()) {
                        if (childChild.getFlag() != null) {
                            flags.add(childChild.getFlag());
                        }
                    }
                }
            }
        }
        return flags;
    }
}
