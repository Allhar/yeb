package com.allhar.server.service;

import com.allhar.server.pojo.Employee;
import com.allhar.server.pojo.EmployeeEc;
import com.allhar.server.pojo.RespPageBean;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author allhar
 * @since 2022-01-10
 */
public interface IEmployeeEcService extends IService<EmployeeEc> {

    /**
     * 获取所欲员(分页)
     * @param currentPage
     * @param size
     * @param employee
     * @param beginDateScope
     * @return
     */
    RespPageBean getEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope);
}
