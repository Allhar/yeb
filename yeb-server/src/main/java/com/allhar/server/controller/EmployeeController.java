package com.allhar.server.controller;


import com.allhar.server.pojo.Employee;
import com.allhar.server.pojo.RespPageBean;
import com.allhar.server.service.IEmployeeEcService;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author allhar
 * @since 2022-01-10
 */
@RestController
@RequestMapping("/employee/basic")
public class EmployeeController {

    @Autowired
    private IEmployeeEcService employeeEcService;

    @ApiOperation(value = "获取所有员工")
    @GetMapping("/")
    public RespPageBean getEmployee(@RequestParam(defaultValue = "1") Integer currentPage,
                                    @RequestParam(defaultValue = "10") Integer size ,
                                    Employee employee ,
                                    LocalDate[] beginDateScope){

        return employeeEcService.getEmployeeByPage(currentPage , size , employee , beginDateScope);

    }

}
