package com.allhar.server.controller;

import com.allhar.server.pojo.Admin;
import com.allhar.server.pojo.AdminLoginParam;
import com.allhar.server.pojo.RespBean;
import com.allhar.server.service.IAdminRoleService;
import com.allhar.server.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * LoginController
 *
 * @author allhar
 * @since 1.0.0
 **/
@Api(tags = "LoginController")
@RestController
public class LoginController {

    @Autowired
    private IAdminService adminService;

    @ApiOperation(value = "登陆,成功之后返回token")
    @PostMapping("/login")
    public RespBean login(@RequestBody AdminLoginParam adminLoginParam , HttpServletRequest request){

        return adminService.login(adminLoginParam.getUsername(),adminLoginParam.getPassword(),adminLoginParam.getCode(),request);

    }


    @ApiOperation(value = "获取当前用户信息")
    @GetMapping("/admin/info")
    public Admin getAdminInfo(Principal principal){

        if (principal == null){
            return null;
        }
        String username = principal.getName();
        Admin admin = adminService.getAdminByUserName(username);
        admin.setPassword(null);
        admin.setRoles(adminService.getRoles(admin.getId()));
        return admin;

    }



    @ApiOperation(value = "登出")
    @PostMapping("/logout")
    public RespBean logout(){
        return RespBean.success("注销成功");
    }




}
