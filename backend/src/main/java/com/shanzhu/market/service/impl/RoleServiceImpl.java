package com.shanzhu.market.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanzhu.market.common.exception.BusinessException;
import com.shanzhu.market.common.redis.service.RedisTemplateService;
import com.shanzhu.market.entity.domain.Employee;
import com.shanzhu.market.entity.domain.Menu;
import com.shanzhu.market.entity.domain.Role;
import com.shanzhu.market.entity.query.RoleQuery;
import com.shanzhu.market.entity.vo.RolePermissonVo;
import com.shanzhu.market.mapper.RoleMapper;
import com.shanzhu.market.service.IEmployeeService;
import com.shanzhu.market.service.IMenuService;
import com.shanzhu.market.service.IRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private IMenuService menuService;

    @Resource
    private RedisTemplateService redisTemplateService;

    @Resource
    private IEmployeeService employeeService;

    @Override
    public Set<Role> queryByEid(Long eid) {
        Set<Role> roles = roleMapper.queryByEid(eid);
        return roles;
    }

    @Override
    public List<Role> listByQo(RoleQuery qo) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.hasText(qo.getName()), "name", qo.getName());
        wrapper.eq(StringUtils.hasText(qo.getState()), "state", qo.getState());
        wrapper.ne("id",Role.SYS_ID);
        wrapper.ne("id",2L);
        List<Role> list = super.list(wrapper);
        return list;
    }

    @Override
    public void forbiddenRole(Long rid) {
        Role role = super.getById(rid);
        if (Role.SYS_ID==role.getId() || 2L==role.getId()) {
            throw new BusinessException("不能停用系统拥有者");
        }
        UpdateWrapper<Role> wrapper = new UpdateWrapper<Role>()
                .set("state", Role.STATE_BAN)
                .eq("id", rid);
        super.update(wrapper);
    }

    @Override
    public void saveRole(Role role) {
        if (role == null) {
            throw new BusinessException("操作失败");
        }
        if (StringUtils.hasText(role.getName())) {
            //查询是否保存过
            Role role1 = super.getOne(new QueryWrapper<Role>().eq("name", role.getName()));
            if (role1 != null) {
                throw new BusinessException("操作失败，角色名重复");
            } else {
                role.setState(Role.STATE_NORMAL);
                super.save(role);
            }
        } else {
            throw new BusinessException("角色名称格式有误");
        }

    }

    @Transactional
    @Override
    public RolePermissonVo checkPermissons(Long rid) {
        RolePermissonVo vo = new RolePermissonVo();
        /*角色所拥有的菜单id集合*/
        List<Long> menuIds = new ArrayList<>();
        /*所有的菜单信息*/
        List<RolePermissonVo.RoleMenu> menus1 = new ArrayList<>();
        //步骤1：查询所有的菜单信息
        List<Menu> menus = menuService.findAll();
        if (menus == null) {
            throw new BusinessException("授权功能还待上线...");
        }
        //封装菜单信息
        for (Menu menu : menus) {
            RolePermissonVo.RoleMenu roleMenu_catalogs = vo.getRoleMenu();
            roleMenu_catalogs.setValue(menu.getId());
            roleMenu_catalogs.setLabel(menu.getLabel());
            /*目录下的菜单*/
            List<RolePermissonVo.RoleMenu> children = new ArrayList<>();
            //目录
            if (menu.getChildren() != null) {
                for (Menu child : menu.getChildren()) {
                    //菜单
                    RolePermissonVo.RoleMenu roleMenu_menu = vo.getRoleMenu();
                    roleMenu_menu.setValue(child.getId());
                    roleMenu_menu.setLabel(child.getLabel());
                    List<RolePermissonVo.RoleMenu> children1 = new ArrayList<>();
                    if (child.getChildren() != null) {
                        for (Menu childChild : child.getChildren()) {
                            //按钮
                            RolePermissonVo.RoleMenu roleMenu_button = vo.getRoleMenu();
                            roleMenu_button.setValue(childChild.getId());
                            roleMenu_button.setLabel(childChild.getLabel());
                            children1.add(roleMenu_button);
                        }
                        roleMenu_menu.setChildren(children1);
                    }
                    children.add(roleMenu_menu);
                }
                roleMenu_catalogs.setChildren(children);
            }
            menus1.add(roleMenu_catalogs);
        }
        vo.setMenus(menus1);

        //步骤2：是否是系统管理员
        if (rid == Role.SYS_ID) {
            //系统管理员
            //封装角色拥有的菜单id集合
            for (Menu menu : menus) {
                menuIds.add(menu.getId());
                //目录
                if (menu.getChildren() != null) {
                    for (Menu child : menu.getChildren()) {
                        //菜单
                        menuIds.add(child.getId());
                        if (child.getChildren() != null) {
                            for (Menu childChild : child.getChildren()) {
                                //按钮
                                menuIds.add(childChild.getId());
                            }
                        }

                    }
                }

            }
            vo.setMenuIds(menuIds);
        } else {
            //非系统管理员
            menuIds = roleMapper.getMenuIdByRid(rid);
            vo.setMenuIds(menuIds);
        }
        return vo;
    }

    @Transactional
    @Override
    public void saveRolePermissons(Long rid, Long[] menuIds) {
        //判断是否是系统管理员
        if (rid == Role.SYS_ID || rid==2L) {
            //系统管理员
            throw new BusinessException("系统管理员的权限不可操作");
        } else {
            //非系统管理员
            //重新建立关系
            if (menuIds == null || menuIds.length == 0) {
                return;
            } else {
                List<Long> ids = new ArrayList<>();
                for (Long menuId : menuIds) {
                    ids.add(menuId);
                }

                Set<Long> totalIds = new HashSet<>();
                QueryWrapper<Menu> qoWrapper = new QueryWrapper<Menu>().in("id", ids);
                List<Menu> list1 = menuService.list(qoWrapper);
                for (Menu menu : list1) {
                    //按钮
                    if (menu.getParentId()!=null && Menu.TYPE_BUTTON.equals(menu.getType())){
                        totalIds.add(menu.getId());
                        QueryWrapper<Menu> btnWrapper = new QueryWrapper<Menu>().eq("id", menu.getParentId())
                                .eq("type",Menu.TYPE_MENU);
                        Menu menu1 = menuService.getOne(btnWrapper);
                        totalIds.add(menu1.getId());
                        totalIds.add(menu1.getParentId());
                    } else if (menu.getParentId()!=null && Menu.TYPE_MENU.equals(menu.getType())){
                        //菜单
                        totalIds.add(menu.getId());
                        totalIds.add(menu.getParentId());
                    }else if (menu.getParentId()==null && Menu.TYPE_CATALOGUE.equals(menu.getType())){
                        //目录
                        totalIds.add(menu.getId());
                    }
                }
                //清除关系
                roleMapper.clearRecordsByRid(rid);
                List<Map<String, Object>> list = new ArrayList<>();
                for (Long id : totalIds) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("rid", rid);
                    map.put("mid", id);
                    list.add(map);
                }
                roleMapper.saveRolePermissons(list);
            }
        }

    }

    @Override
    public List<Map<String, Object>> getRoleAll() {
        List<Map<String, Object>> list = new ArrayList<>();
        QueryWrapper<Role> wrapper = new QueryWrapper<Role>().eq("state", Role.STATE_NORMAL).ne("id",Role.SYS_ID).ne("id",2L);
        List<Role> roles = super.list(wrapper);
        for (Role role : roles) {
            Map<String, Object> map=new HashMap<>();
            map.put("id",role.getId());
            map.put("label",role.getName());
            list.add(map);
        }
        return list;
    }

    @Override
    public List<Long> queryRoleIdsByEid(Long eid) {
        Employee emp = employeeService.getById(eid);
        if (emp.getIsAdmin()){
            return roleMapper.queryRoleIdsAll();
        }else {
            return roleMapper.queryRoleIdsByEid(eid);
        }
    }

    @Override
    public void saveRoleEmp(Long eid, Long[] roleIds,String token) {
        Employee emp = JSONObject.parseObject(redisTemplateService.getCacheObject(token), Employee.class);
        if (emp.getId()==eid){
            throw new BusinessException("无法为自己赋予职务");
        }
        //查询用户的信息，判断是否是系统管理员
        Employee employee = employeeService.getById(eid);
        if (employee.getIsAdmin()){
            throw new BusinessException("无法操作系统管理员的职务");
        }
        /*根据员工编号清除关系*/
        roleMapper.clearRelationByEid(eid);
        /*重新保存关系*/
        List<Map<String,Object>> list=new ArrayList<>();
        for (Long roleId : roleIds) {
            Map<String, Object> map = new HashMap<>();
            map.put("eid",eid);
            map.put("rid",roleId);
            list.add(map);
        }
        if (list.size()>0){
            roleMapper.reSaveRelation(list);
        }
    }

    @Override
    public void clearEmpPermission(Long id) {
        roleMapper.clearRelationByEid(id);
    }
}
