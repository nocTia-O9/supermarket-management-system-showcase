package com.shanzhu.market.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shanzhu.market.entity.domain.Menu;
import com.shanzhu.market.entity.query.MenuQuery;

import java.util.List;
import java.util.Set;

public interface IMenuService extends IService<Menu> {

    /**
     * 查询所有的菜单
     */
    List<Menu> findAll();

    /**
     * 根据角色id集合查询对应的菜单
     */
    List<Menu> queryByRids(Set<Long> rids);

    /**
     * 分页查询菜单的信息
     * 以目录进行分页
     */
    Page<Menu> queryPageByQo(MenuQuery qo);

    /**
     * 查询父菜单id
     */
    List<Long> listParentByIds(List<Long> ids);

}
