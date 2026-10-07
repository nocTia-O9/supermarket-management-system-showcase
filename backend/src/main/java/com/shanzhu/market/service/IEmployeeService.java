package com.shanzhu.market.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shanzhu.market.entity.domain.Employee;
import com.shanzhu.market.entity.query.QueryEditPwd;
import com.shanzhu.market.entity.query.QueryEmp;
import com.shanzhu.market.entity.vo.DetailEmpVo;
import com.shanzhu.market.entity.vo.EditEmpVo;
import com.shanzhu.market.entity.vo.InformationVo;

public interface IEmployeeService  extends IService<Employee> {

    /**
     * 修改个人密码业务
     */
    void edit_pwd(QueryEditPwd editPwd, String token);

    /**
     * 分页显示数据
     */
    Page<Employee> pageByQo(QueryEmp qo);

    DetailEmpVo detail(Long uid);

    void saveEmp(Employee employee,String token);

    EditEmpVo editbtn(Long uid);

    void updateEmp(Employee employee,String token);

    void deactivate(Long id);

    /**
     * 重置密码
     */
    void resetPwd(Long eid, String code);

    InformationVo information(String token);
}
