package com.shanzhu.market.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanzhu.market.common.exception.BusinessException;
import com.shanzhu.market.common.redis.constants.RedisKeys;
import com.shanzhu.market.common.redis.service.RedisTemplateService;
import com.shanzhu.market.entity.domain.Dept;
import com.shanzhu.market.entity.domain.Employee;
import com.shanzhu.market.entity.domain.Role;
import com.shanzhu.market.entity.query.QueryDept;
import com.shanzhu.market.entity.query.QueryEditPwd;
import com.shanzhu.market.entity.query.QueryEmp;
import com.shanzhu.market.entity.vo.DetailEmpVo;
import com.shanzhu.market.entity.vo.EditEmpVo;
import com.shanzhu.market.entity.vo.InformationVo;
import com.shanzhu.market.mapper.EmployeeMapper;
import com.shanzhu.market.service.IDeptService;
import com.shanzhu.market.service.IEmployeeService;
import com.shanzhu.market.service.IRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Resource
    private RedisTemplateService redisTemplateService;

    @Resource
    private IDeptService deptService;

    @Resource
    private IRoleService roleService;

    @Override
    public void edit_pwd(QueryEditPwd editPwd, String token) {
        //获取缓存中的登录员工信息
        String str = redisTemplateService.getCacheObject(token);
        Employee employee = JSONObject.parseObject(str, Employee.class);
        if (employee.getId() == 1L) {
            throw new BusinessException("该账户不能被修改");
        }
        //比对用户名是否一致
        if (!employee.getUsername().equals(editPwd.getUsername())) {
            //不一致
            throw new BusinessException("没有权限修改其他人的密码");
        }
        //比对旧密码是否输入正确
        if (!employee.getPassword().equals(editPwd.getOldPwd())) {
            throw new BusinessException("原密码输入有误");
        }
        //比对新密码和旧密码是否一致
        if (editPwd.getOldPwd().equals(editPwd.getNewPwd())) {
            throw new BusinessException("新密码和旧密码一致");
        }
        UpdateWrapper<Employee> wrapper = new UpdateWrapper<Employee>().set("password", editPwd.getNewPwd())
                .eq("phone", editPwd.getUsername());
        super.update(wrapper);
    }

    @Transactional
    @Override
    public Page<Employee> pageByQo(QueryEmp qo) {
        Page<Employee> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        QueryWrapper<Employee> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.hasText(qo.getUsername()), "phone", qo.getUsername())
                .eq(StringUtils.hasText(qo.getAge()), "age", qo.getAge())
                .like(StringUtils.hasText(qo.getNickName()), "nick_name", qo.getNickName())
                .like(StringUtils.hasText(qo.getAddress()), "address", qo.getAddress())
                .eq(StringUtils.hasText(qo.getSex()), "sex", qo.getSex())
                .ne("id", 1L)
                .eq(qo.getDeptId() != null, "deptId", qo.getDeptId());
        super.page(page, wrapper);
        //补全部门信息
        List<Dept> depts = deptService.listByQo(new QueryDept());
        List<Employee> records = page.getRecords();
        if (records != null) {
            for (Employee record : records) {
                if (depts != null) {
                    for (Dept dept : depts) {
                        if (dept.getId() == record.getDeptId()) {
                            record.setDeptName(dept.getName());
                            break;
                        }
                    }
                }
            }
        }
        return page;
    }

    @Override
    public DetailEmpVo detail(Long uid) {
        DetailEmpVo vo = new DetailEmpVo();
        //查询员工信息
        Employee employee = super.getById(uid);
        BeanUtils.copyProperties(employee, vo);

        //补全角色信息
        Set<String> roleNames = new HashSet<>();
        if (employee.getIsAdmin() == true) {
            //查询所有角色
            List<Role> list = roleService.list();
            for (Role role : list) {
                roleNames.add(role.getName());

            }
        } else {
            Set<Role> roles = roleService.queryByEid(uid);
            for (Role role : roles) {
                roleNames.add(role.getName());
            }
        }
        vo.setRoleNames(roleNames);
        return vo;
    }

    @Override
    public void saveEmp(Employee employee, String token) {
        //校验参数是否有误
        if (!StringUtils.hasLength(employee.getPassword())) {
            employee.setPassword(Employee.DEFAULT_PWD);
        }
        if (!StringUtils.hasLength(employee.getUsername())) {
            throw new BusinessException("手机号不能为空");
        }
        if (!StringUtils.hasLength(employee.getIdCard())) {
            throw new BusinessException("身份证号不能为空");
        }
        if (employee.getAge() == null) {
            employee.setAge(18);
        } else if (employee.getAge() < 0 || employee.getAge() > 120) {
            throw new BusinessException("年龄值有误");
        }
        //校验用户是否已注册
        QueryWrapper<Employee> wrapper = new QueryWrapper<Employee>().eq("phone", employee.getUsername());
        Employee one = super.getOne(wrapper);
        if (one != null) {
            throw new BusinessException("系统中已存在该账户");
        }
        employee.setState(Employee.STATE_NORMAL);
        if (!StringUtils.hasText(employee.getSex())) {
            employee.setSex(Employee.SEX_MEN);
        }
        if (!StringUtils.hasText(employee.getHeadImg())) {
            employee.setHeadImg(Employee.DEFAULT_HEAD_IMG);
        }
        employee.setCreateTime(new Date());
        employee.setIsAdmin(false);
        String nickName = JSONObject.parseObject(redisTemplateService.getCacheObject(token), Employee.class).getNickName();
        employee.setCreateby(nickName);
        super.save(employee);
    }

    @Override
    public EditEmpVo editbtn(Long uid) {
        EditEmpVo vo = new EditEmpVo();
        Employee employee = super.getById(uid);
        BeanUtils.copyProperties(employee, vo);
        return vo;
    }

    @Override
    public void updateEmp(Employee employee, String token) {
        if (Employee.STATE_DEL.equals(employee.getState())) {
            if (employee.getIsAdmin()) {
                throw new BusinessException("不可以给系统管理者办理离职");
            }
        }
        QueryWrapper<Employee> wrapper = new QueryWrapper<Employee>().having("id!=" + employee.getId())
                .eq(StringUtils.hasText(employee.getUsername()), "phone", employee.getUsername())
                .or()
                .eq(StringUtils.hasText(employee.getIdCard()), "id_card", employee.getIdCard());
        List<Employee> list = super.list(wrapper);
        if (list != null && list.size() > 0) {
            throw new BusinessException("系统已存在相同的用户名或身份证号");
        }

        if (Employee.STATE_DEL.equals(employee.getState())) {
            employee.setLeaveTime(new Date());
        } else {
            employee.setCreateTime(new Date());
            String nickName = JSONObject.parseObject(redisTemplateService.getCacheObject(token), Employee.class).getNickName();
            employee.setCreateby(nickName);
        }
        super.updateById(employee);
    }

    @Override
    public void deactivate(Long id) {
        Employee employee = super.getById(id);
        if (employee.getIsAdmin()) {
            throw new BusinessException("不可以给系统管理者办理离职");
        }
        if (Employee.STATE_DEL.equals(employee.getState())) {
            throw new BusinessException("已是离职状态");
        }
        UpdateWrapper<Employee> wrapper = new UpdateWrapper<Employee>().set("state", Employee.STATE_DEL).eq("id", id);
        super.update(wrapper);
    }

    @Transactional
    @Override
    public void resetPwd(Long eid, String code) {
        Employee employee = super.getById(eid);
        if (employee.getId() == 1L) {
            throw new BusinessException("该账户不可被修改");
        }
        if (employee.getIsAdmin()) {
            if (code.equals("123456")) {
                UpdateWrapper<Employee> wrapper = new UpdateWrapper<Employee>().set("password", Employee.DEFAULT_PWD).eq("id", eid);
                super.update(wrapper);
            } else {
                throw new BusinessException("密钥错误");
            }
        } else {
            if (code.equals("456789")) {
                UpdateWrapper<Employee> wrapper = new UpdateWrapper<Employee>().set("password", Employee.DEFAULT_PWD).eq("id", eid);
                super.update(wrapper);
            } else {
                throw new BusinessException("密钥错误");
            }
        }
        redisTemplateService.deleteObject(RedisKeys.LOGIN_USER.join(employee.getUsername()));
        redisTemplateService.deleteObject(RedisKeys.DISABLEUSER.join(employee.getUsername()));
        redisTemplateService.deleteObject(RedisKeys.LOGIN_ERRO_PWDNUM.join(employee.getUsername()));
    }

    @Override
    public InformationVo information(String token) {
        Employee employee = JSONObject.parseObject(redisTemplateService.getCacheObject(token), Employee.class);
        DetailEmpVo detail = detail(employee.getId());
        InformationVo vo = new InformationVo();
        BeanUtils.copyProperties(detail, vo);
        return vo;

    }
}
