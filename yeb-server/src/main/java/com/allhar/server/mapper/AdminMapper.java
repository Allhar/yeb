package com.allhar.server.mapper;

import com.allhar.server.pojo.Admin;
import com.allhar.server.pojo.Menu;
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
public interface AdminMapper extends BaseMapper<Admin> {

    /**
     * 根据id查询menu
     * @param id
     * @return
     */
    List<Menu> getMenusByAdminId(Integer id);
}
