package com.shanzhu.market.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shanzhu.market.entity.domain.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {


    /**
     * 根据角色id集合查询权限信息
     * @param rids
     * @return
     */
    List<Menu> queryByRids(Set<Long> rids);


}
