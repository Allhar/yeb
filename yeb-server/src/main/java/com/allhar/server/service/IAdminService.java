package com.allhar.server.service;

import com.allhar.server.pojo.Admin;
import com.allhar.server.pojo.RespBean;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author allhar
 * @since 2022-01-10
 */
public interface IAdminService extends IService<Admin> {

    RespBean login(String username, String password, HttpServletRequest request);

    Admin getAdminByUserName(String username);
}