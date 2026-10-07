package com.shanzhu.market.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanzhu.market.entity.domain.Menu;
import com.shanzhu.market.entity.query.MenuQuery;
import com.shanzhu.market.mapper.MenuMapper;
import com.shanzhu.market.service.IMenuService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public List<Menu> findAll() {

        //查询目录菜单
        // select * from t_menu where type='0' and state='0'
        QueryWrapper<Menu> wrapper1 = new QueryWrapper<Menu>().eq("type", Menu.TYPE_CATALOGUE).eq("state", Menu.STATE_NORMAL);
        List<Menu> catalogs = super.list(wrapper1);
        if (catalogs.isEmpty()) {
            return null;
        }
        //得到目录下的菜单信息
        for (Menu catalog : catalogs) {
            //select * from t_menu where type='1' and parent_id=#{id} and state='0'
            QueryWrapper<Menu> wrapper2 = new QueryWrapper<Menu>().eq("type", Menu.TYPE_MENU).eq("state", Menu.STATE_NORMAL)
                    .eq("parent_id", catalog.getId());
            List<Menu> menus = super.list(wrapper2);
            //获取菜单下的按钮
            for (Menu menu : menus) {
                //select * from t_menu where type='2' and parent_id=#{id} and state='0'
                QueryWrapper<Menu> wrapper3 = new QueryWrapper<Menu>().eq("type", Menu.TYPE_BUTTON).eq("state", Menu.STATE_NORMAL)
                        .eq("parent_id", menu.getId());
                List<Menu> buttons = super.list(wrapper3);
                if (!buttons.isEmpty()) {
                    menu.setChildren(buttons);
                }
            }
            catalog.setChildren(menus);
        }
        return catalogs;
    }

    @Override
    public List<Menu> queryByRids(Set<Long> rids) {
        List<Menu> result = menuMapper.queryByRids(rids);
        if (result.isEmpty()) {
            return null;
        }
        //目录
        Set<Menu> catalogs = new HashSet<>();
        //菜单
        Set<Menu> menus = new HashSet<>();
        //按钮
        Set<Menu> buttons = new HashSet<>();

        Iterator<Menu> iterator1 = result.iterator();
        while (iterator1.hasNext()) {
            Menu item = iterator1.next();
            switch (item.getType()) {
                case Menu.TYPE_CATALOGUE:
                    catalogs.add(item);
                    break;
                case Menu.TYPE_MENU:
                    menus.add(item);
                    break;
                case Menu.TYPE_BUTTON:
                    buttons.add(item);
                    break;
            }
            iterator1.remove();
        }

        for (Menu catalog : catalogs) {
            catalog.setChildren(new ArrayList<>());
            for (Menu menu : menus) {
                menu.setChildren(new ArrayList<>());
                for (Menu button : buttons) {
                    //将按钮分配到对应的菜单下
                    if (button.getParentId() == menu.getId()) {
                        menu.getChildren().add(button);
                    }
                }
                //将菜单分配到对应的目录下
                if (menu.getParentId() == catalog.getId()) {
                    catalog.getChildren().add(menu);
                }
            }

        }
        List<Menu> catalogues = new ArrayList<>();
        //去重
        for (Menu catalog : catalogs) {
            catalogues.add(catalog);
        }
        return catalogues;
    }

    @Override
    public Page<Menu> queryPageByQo(MenuQuery qo) {
        Page<Menu> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        //查询目录
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.eq("type", Menu.TYPE_CATALOGUE);
        wrapper.like(StringUtils.hasText(qo.getName()), "label", qo.getName());

        Page<Menu> page1 = super.page(page, wrapper);
        //补全目录的子集
        List<Menu> catalogs = page1.getRecords();
        if (catalogs == null || catalogs.isEmpty()) {
            return page1;
        }
        /*补全目录下的菜单*/
        for (Menu catalog : catalogs) {
            QueryWrapper<Menu> wrapper_menu = new QueryWrapper<Menu>()
                    .eq("type", Menu.TYPE_MENU)
                    .eq("parent_id", catalog.getId());
            List<Menu> menus = super.list(wrapper_menu);
            if (menus == null || menus.isEmpty()) {
                continue;
            }
            /*补全菜单下的按钮*/
            for (Menu menu : menus) {
                QueryWrapper<Menu> wrapper_button = new QueryWrapper<Menu>()
                        .eq("type", Menu.TYPE_BUTTON)
                        .eq("parent_id", menu.getId());
                List<Menu> buttons = super.list(wrapper_button);
                if (buttons == null || buttons.isEmpty()) {
                    continue;
                }
                menu.setChildren(buttons);
            }
            catalog.setChildren(menus);
        }
        return page1;
    }

    @Override
    public List<Long> listParentByIds(List<Long> ids) {
        List<Long> parentIds = new ArrayList<>();
        ArrayList<Long> btnIds = new ArrayList<>();
        QueryWrapper<Menu> wrapper = new QueryWrapper<Menu>().in("id", ids);
        List<Menu> list = super.list(wrapper);
        if (list==null){
            return parentIds;
        }
        for (Menu menu : list) {
           if (Menu.TYPE_MENU.equals(menu.getType())){
                //菜单
               parentIds.add(menu.getParentId());
            }else {
                //按钮
               parentIds.add(menu.getParentId());
               btnIds.add(menu.getId());
            }
        }
        return parentIds;
    }
}
