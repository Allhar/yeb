package com.allhar.server.mapper;

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
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> getMenusByAdminId(Integer id);

    /**
     * 根据角色查找菜单列表
     * @return
     */
    List<Menu> getMenusByRole();
}
