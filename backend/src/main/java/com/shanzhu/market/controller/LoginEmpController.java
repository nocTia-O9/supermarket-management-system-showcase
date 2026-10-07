package com.shanzhu.market.controller;

import com.shanzhu.market.common.sercurity.annotation.NoRequireLogin;
import com.shanzhu.market.common.web.response.JsonResult;
import com.shanzhu.market.entity.domain.Menu;
import com.shanzhu.market.service.ILoginService;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;


@RestController
@Validated
public class LoginEmpController {

    @Resource
    private ILoginService loginService;

    /**
     * 登录功能
     */
    @NoRequireLogin
    @PostMapping("/login")
    public JsonResult login(@NotEmpty(message = "账号不能为空") String username, @NotEmpty(message = "密码不能为空") String password) {
        Map<String, Object> map = loginService.login(username, password);
        return JsonResult.success(map);

    }

    /**
     * 退出功能
     */
    @NoRequireLogin
    @GetMapping("/exit")
    public JsonResult exit(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StringUtils.hasLength(token)){
            loginService.exit(token);
        }
        return JsonResult.success();
    }

    @PostMapping("/logout")
    public JsonResult logout(@NotEmpty(message = "内容不能为空") String content,HttpServletRequest request) {

        loginService.logout(request.getHeader("token"), content);

        return JsonResult.success();
    }

    /**
     * 查询登录者的拥有的菜单
     */
    @GetMapping("/empMenu")
    public JsonResult empMenu(HttpServletRequest request){
        List<Menu> menus=loginService.empMenu((String)request.getHeader("token"));
        return JsonResult.success(menus);
    }

    @NoRequireLogin
    @GetMapping("/checkedToken")
    public JsonResult checkedToken(String token){
        Map<String,Object> map=loginService.checkedToken(token);
        return JsonResult.success(map);
    }

}
