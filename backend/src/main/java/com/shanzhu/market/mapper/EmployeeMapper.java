package com.shanzhu.market.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shanzhu.market.entity.domain.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
