package com.shanzhu.market.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shanzhu.market.entity.domain.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 根据员工id查询角色信息
     *
     * @param eid
     * @return
     */
    Set<Role> queryByEid(Long eid);

    /**
     * 根据角色id查询菜单信息
     *
     * @param rid
     * @return
     */
    List<Long> getMenuIdByRid(Long rid);

    /**
     * 清除关系
     *
     * @param rid
     */
    void clearRecordsByRid(Long rid);


    /**
     * 保存关系
     *
     * @param roleMenu
     */
    void saveRolePermissons(List<Map<String,Object>> roleMenu);

    List<Long> queryRoleIdsByEid(Long eid);

    void clearRelationByEid(Long eid);

    void reSaveRelation(List<Map<String, Object>> list);

    List<Long> queryRoleIdsAll();

}
