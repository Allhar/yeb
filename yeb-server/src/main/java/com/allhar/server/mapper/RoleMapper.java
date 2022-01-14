package com.allhar.server.mapper;

import com.allhar.server.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author allhar
 * @since 2022-01-10
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户id查找角色
     * @param adminId
     * @return
     */
    List<Role> getRoles(Integer adminId);
}
