package com.allhar.server.service;

import com.allhar.server.pojo.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author allhar
 * @since 2022-01-10
 */
public interface IMenuService extends IService<Menu> {

    /**
     *    "通过用户ID查询菜单列表")
     * @return
     */
    List<Menu> getMenusByAdminId();
}
