package com.allhar.server.service.impl;

import com.allhar.server.AdminUtils;
import com.allhar.server.config.security.JwtTokenUtil;
import com.allhar.server.mapper.AdminRoleMapper;
import com.allhar.server.mapper.RoleMapper;
import com.allhar.server.pojo.*;
import com.allhar.server.mapper.AdminMapper;
import com.allhar.server.service.IAdminService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author allhar
 * @since 2022-01-10
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private AdminMapper adminMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AdminRoleMapper adminRoleMapper;
    /**
     * 登陆之后返回token
     * @param username
     * @param password
     * @param code
     * @param request
     * @return
     */
    @Override
    public RespBean login(String username, String password, String code, HttpServletRequest request) {

        String captcha = (String) request.getSession().getAttribute("captcha");
        if(StringUtil.isNullOrEmpty(code)||!captcha.equalsIgnoreCase(code)){
            return RespBean.error("验证码输入错误");
        }

        UserDetails userDetails = null;
        try {
            userDetails = userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            return RespBean.error("用户名或密码不正确");
        }
        if (null == userDetails || !passwordEncoder.matches(password, userDetails.getPassword())) {
            return RespBean.error("用户名或密码不正确");
        }
        if (!userDetails.isEnabled()) {
            return RespBean.error("账号被禁用，请联系管理员！");
        }
        //更新security登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //生成token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String,String> tokenMap= new HashMap<>();
        tokenMap.put("tokenHead",tokenHead);
        tokenMap.put("token",token);

        System.out.println(token);

        return RespBean.success("登陆成功",tokenMap);

    }

    @Override
    public Admin getAdminByUserName(String username) {
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username",username).eq("enabled",true));
    }


    /**
     * 根据用户id查找角色
     * @param adminId
     * @return
     */
    @Override
    public List<Role> getRoles(Integer adminId) {
        return roleMapper.getRoles(adminId);
    }


    /**
     * 获取所有操作员
     * @return
     */
    @Override
    public List<Admin> getAllAdmins(String keyWords) {
        return adminMapper.getAllAdmins(AdminUtils.getCurrentAdmin().getId() , keyWords);
    }

    /**
     * 更新操作员角色
     * @param adminId
     * @param rids
     * @return
     */
    @Override
    @Transactional
    public RespBean updateAdminRole(Integer adminId, Integer[] rids) {
        adminRoleMapper.delete((new QueryWrapper<AdminRole>().eq("adminID",adminId)));

        Integer result = adminRoleMapper.addAdminRole(adminId, rids);
        if (rids.length==result){
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }


}
