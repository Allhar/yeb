package com.allhar.server.service.impl;

import com.allhar.server.mapper.EmployeeMapper;
import com.allhar.server.pojo.Employee;
import com.allhar.server.pojo.EmployeeEc;
import com.allhar.server.mapper.EmployeeEcMapper;
import com.allhar.server.pojo.RespPageBean;
import com.allhar.server.service.IEmployeeEcService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author allhar
 * @since 2022-01-10
 */
@Service
public class EmployeeEcServiceImpl extends ServiceImpl<EmployeeEcMapper, EmployeeEc> implements IEmployeeEcService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 获取所欲员(分页)
     * @param currentPage
     * @param size
     * @param employee
     * @param beginDateScope
     * @return
     */
    @Override
    public RespPageBean getEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope) {
        Page<Employee> page = new Page<>(currentPage , size);
        IPage employeeByPage = employeeMapper.getEmployeeByPage(page, employee, beginDateScope);
        RespPageBean respPageBean = new RespPageBean(employeeByPage.getTotal() , employeeByPage.getRecords());
        return respPageBean;
    }
}
