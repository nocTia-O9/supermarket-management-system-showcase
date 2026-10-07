package com.shanzhu.market.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shanzhu.market.entity.domain.Employee;
import com.shanzhu.market.entity.domain.Menu;

import java.util.List;
import java.util.Map;

/**
 * 员工登录业务接口
 */
public interface ILoginService extends IService<Employee> {

    /**
     * 处理员工登录业务
     */
    Map<String,Object> login(String username, String password);

    /**
     * 退出功能
     */
    void exit(String token);

    /**
     * 注销账户功能
     */
    void logout(String token, String content);

    /**
     * 登录者拥有的菜单信息
     */
    List<Menu> empMenu(String token);

    Map<String, Object> checkedToken(String token);
}
