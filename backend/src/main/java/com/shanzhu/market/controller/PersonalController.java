package com.shanzhu.market.controller;

import com.shanzhu.market.common.sercurity.annotation.HasPermisson;
import com.shanzhu.market.common.web.response.JsonResult;
import com.shanzhu.market.entity.query.QueryEditPwd;
import com.shanzhu.market.entity.vo.InformationVo;
import com.shanzhu.market.service.IEmployeeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@Validated
@RequestMapping("/personal")
public class PersonalController {

    @Resource
    private IEmployeeService employeeService;

    /**
     * 修改个人的密码
     */
    @HasPermisson("personal:edit_pwd")
    @PostMapping("/edit_pwd")
    public JsonResult edit_pwd(HttpServletRequest request, QueryEditPwd editPwd){
        String token = request.getHeader("token");
        employeeService.edit_pwd(editPwd,token);
        return JsonResult.success();
    }

    @HasPermisson("personnel_management:employee:update")
    @GetMapping("/information")
    public JsonResult information(HttpServletRequest request){
        String token = request.getHeader("token");
        InformationVo vo=employeeService.information(token);
        return JsonResult.success(vo);
    }
}
