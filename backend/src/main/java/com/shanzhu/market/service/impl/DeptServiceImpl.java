package com.shanzhu.market.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanzhu.market.common.exception.BusinessException;
import com.shanzhu.market.entity.domain.Dept;
import com.shanzhu.market.entity.domain.Employee;
import com.shanzhu.market.entity.query.QueryDept;
import com.shanzhu.market.mapper.DeptMapper;
import com.shanzhu.market.service.IDeptService;
import com.shanzhu.market.service.IEmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

    @Resource
    private IEmployeeService employeeService;

    @Override
    public List<Dept> listByQo(QueryDept qo) {
        LambdaQueryWrapper<Dept> wrapper = new LambdaQueryWrapper<Dept>()
                .like(StringUtils.hasText(qo.getName()),Dept::getName, qo.getName())
                .eq(StringUtils.hasText(qo.getState()),Dept::getState, qo.getState());
        return super.list(wrapper);
    }

    @Transactional
    @Override
    public void forbiddenRole(Long id) {
        QueryWrapper<Employee> empWrapper = new QueryWrapper<Employee>().eq(id != null,"deptId", id);
        List<Employee> list = employeeService.list(empWrapper);
        if (list!=null &&list.size()>0){
            throw new BusinessException("操作失败，该部门正在使用");
        }
        UpdateWrapper<Dept> wrapper = new UpdateWrapper<Dept>().set("state", Dept.STATE_BAN).eq("id", id);
        super.update(wrapper);
    }

    @Transactional
    @Override
    public void saveDept(Dept dept) {
        //判断是否有被创建
        QueryWrapper<Dept> wrapper = new QueryWrapper<Dept>().eq(StringUtils.hasText(dept.getName()), "name", dept.getName());
        if (super.getOne(wrapper)!=null){
            throw new BusinessException("操作失败，该部门已存在");
        }
        dept.setState(Dept.STATE_NORMAL);
        super.save(dept);

    }

    @Transactional
    @Override
    public void updateDept(Dept dept) {
        if (Dept.STATE_BAN.equals(dept.getState())){
            QueryWrapper<Employee> empWrapper = new QueryWrapper<Employee>().eq(dept.getId() != null,"deptId", dept.getId());
            List<Employee> list = employeeService.list(empWrapper);
            if (list!=null &&list.size()>0){
                throw new BusinessException("操作失败，该部门正在使用");
            }
        }
        super.updateById(dept);
    }
}
