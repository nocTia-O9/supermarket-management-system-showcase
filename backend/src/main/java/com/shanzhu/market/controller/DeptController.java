package com.shanzhu.market.controller;

import com.shanzhu.market.common.sercurity.annotation.HasPermisson;
import com.shanzhu.market.common.web.response.JsonResult;
import com.shanzhu.market.entity.domain.Dept;
import com.shanzhu.market.entity.query.QueryDept;
import com.shanzhu.market.service.IDeptService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Validated
@RequestMapping("/personnel_management/dept")
public class DeptController {

    @Resource
    private IDeptService deptService;

    /*保存信息接口*/
    @HasPermisson("personnel_management:dept:save")
    @PostMapping("/save")
    public JsonResult saveDept(Dept dept){
        deptService.saveDept(dept);
        return JsonResult.success();
    }

    /*修改接口*/
    @HasPermisson("personnel_management:dept:update")
    @PostMapping("/update")
    public JsonResult updateDept(Dept dept){
        deptService.updateDept(dept);
        return JsonResult.success();
    }

    /*停用*/
    @HasPermisson("personnel_management:dept:deactivate")
    @PostMapping("/deactivate")
    public JsonResult deactivate(Long id){
        deptService.forbiddenRole(id);
        return JsonResult.success();
    }
    /*查询信息*/
    @GetMapping("/list")
    public JsonResult listByQo(QueryDept qo){
        return JsonResult.success(deptService.listByQo(qo));
    }
}
