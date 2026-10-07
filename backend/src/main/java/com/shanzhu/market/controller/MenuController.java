package com.shanzhu.market.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shanzhu.market.common.sercurity.annotation.HasPermisson;
import com.shanzhu.market.common.web.response.JsonResult;
import com.shanzhu.market.entity.domain.Menu;
import com.shanzhu.market.entity.query.MenuQuery;
import com.shanzhu.market.service.IMenuService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Resource
    private IMenuService menuService;

    /**
     * 条件分页查询菜单的信息
     */
    @HasPermisson("system:menu:list")
    @PostMapping("/queryPageByQo")
    public JsonResult queryPageByQo(MenuQuery qo) {
        Page<Menu> page = menuService.queryPageByQo(qo);
        return JsonResult.success(page);
    }

}
