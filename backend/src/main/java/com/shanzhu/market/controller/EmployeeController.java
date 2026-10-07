package com.shanzhu.market.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shanzhu.market.common.sercurity.annotation.HasPermisson;
import com.shanzhu.market.common.sercurity.annotation.NoRequireLogin;
import com.shanzhu.market.common.util.PathUtils;
import com.shanzhu.market.common.web.response.JsonResult;
import com.shanzhu.market.entity.domain.Employee;
import com.shanzhu.market.entity.query.QueryEmp;
import com.shanzhu.market.entity.vo.DetailEmpVo;
import com.shanzhu.market.entity.vo.EditEmpVo;
import com.shanzhu.market.service.IEmployeeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/personnel_management/employee")
public class EmployeeController {

    @Resource
    private IEmployeeService employeeService;

    @HasPermisson("personnel_management:employee:list")
    @PostMapping("/list")
    public JsonResult pageByQo(QueryEmp qo) {
        Page<Employee> page = employeeService.pageByQo(qo);
        return JsonResult.success(page);
    }

    @HasPermisson("personnel_management:employee:list")
    @GetMapping("/detail")
    public JsonResult detail(Long uid) {
        DetailEmpVo vo = employeeService.detail(uid);
        return JsonResult.success(vo);
    }

    /**
     * 上传图片到阿里云oss
     * 返回网络图片地址,uploaded:1:成功 0:失败
     */
    @NoRequireLogin
    @PostMapping("/uploadImg")
    public Map<String, Object> uploadImg(@RequestParam("file") MultipartFile upload) {
        Map<String, Object> map = new HashMap<>();
        if (upload != null && upload.getSize() > 0) {
            String path;
            try {
                path = PathUtils.upload(upload);
                map.put("uploaded", 1);  //成功
                map.put("url", path);  //成功
            } catch (Exception e) {
                e.printStackTrace();
                map.put("uploaded", 0);  //失败
                Map<String, Object> errorMap = new HashMap<>();
                errorMap.put("message", e.getMessage());
                map.put("error", errorMap);
            }
        } else {
            map.put("uploaded", 0);  //失败
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("message", "上传失败，图片文件异常");
            map.put("error", errorMap);
        }
        return map;
    }

    /*保存*/
    @HasPermisson("personnel_management:employee:save")
    @PostMapping("/save")
    public JsonResult saveEmp(Employee employee, HttpServletRequest request) {
        employeeService.saveEmp(employee, (String) request.getHeader("token"));
        return JsonResult.success();
    }

    /*修改按钮*/
    @HasPermisson("personnel_management:employee:update")
    @GetMapping("/editbtn")
    public JsonResult editbtn(Long uid) {
        EditEmpVo vo = employeeService.editbtn(uid);
        return JsonResult.success(vo);
    }

    /*修改员工业务*/
    @HasPermisson("personnel_management:employee:update")
    @PostMapping("/update")
    public JsonResult updateEmp(Employee employee, HttpServletRequest request) {
        employeeService.updateEmp(employee, (String) request.getHeader("token"));
        return JsonResult.success();
    }

    /*离职业务*/
    @HasPermisson("personnel_management:employee:update")
    @PostMapping("/deactivate")
    public JsonResult deactivate(Long id) {
        employeeService.deactivate(id);
        return JsonResult.success();
    }

    /*更改密码*/
    @HasPermisson("personnel_management:employee:resetPwd")
    @PostMapping("/resetPwd")
    public JsonResult resetPwd(Long eid, String code) {
        employeeService.resetPwd(eid, code);
        return JsonResult.success();
    }
}
