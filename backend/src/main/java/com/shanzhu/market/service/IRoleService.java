package com.shanzhu.market.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shanzhu.market.entity.domain.Role;
import com.shanzhu.market.entity.query.RoleQuery;
import com.shanzhu.market.entity.vo.RolePermissonVo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IRoleService  extends IService<Role> {

    /**
     * 查询该员工id的角色信息
     */
    Set<Role> queryByEid(Long eid);

    /**
     * 条件查询角色信息
     */
    List<Role> listByQo(RoleQuery qo);

    /**
     * 停用角色业务
     */
    void forbiddenRole(Long rid);

    /**
     * 保存角色信息业务
     */
    void saveRole(Role role);

    /**
     * 查看角色权限的业务
     */
    RolePermissonVo checkPermissons(Long rid);

    /**
     * 保存角色-菜单的关系业务
     */
    void saveRolePermissons(Long rid, Long[] menuIds);

    List<Map<String, Object>> getRoleAll();

    List<Long> queryRoleIdsByEid(Long eid);

    void saveRoleEmp(Long eid, Long[] roleIds,String token);

    void clearEmpPermission(Long id);
}
