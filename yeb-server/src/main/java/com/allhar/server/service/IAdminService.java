package com.allhar.server.service;

import com.allhar.server.pojo.Admin;
import com.allhar.server.pojo.Menu;
import com.allhar.server.pojo.RespBean;
import com.allhar.server.pojo.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author allhar
 * @since 2022-01-10
 */
public interface IAdminService extends IService<Admin> {

    RespBean login(String username, String password, String code, HttpServletRequest request);

    Admin getAdminByUserName(String username);

    /**
     * 根据用户id查找角色
     * @param adminId
     * @return
     */
    List<Role> getRoles(Integer adminId);

    /**
     * 获取所有操作员
     * @return
     */
    List<Admin> getAllAdmins(String keyWords);
}
