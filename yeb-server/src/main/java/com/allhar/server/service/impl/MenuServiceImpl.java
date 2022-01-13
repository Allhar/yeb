package com.allhar.server.service.impl;

import com.allhar.server.pojo.Admin;
import com.allhar.server.pojo.Menu;
import com.allhar.server.mapper.MenuMapper;
import com.allhar.server.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author allhar
 * @since 2022-01-10
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    /**
     *  "通过用户ID查询菜单列表")
     * @return
     */
    @Override
    public List<Menu> getMenusByAdminId() {
        Integer adminId = ((Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();

        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        //从redis获取菜单数据
        List<Menu> menus = (List<Menu>) valueOperations.get("menu_" + adminId);
        //如果redis获取数据为空,则从数据库获取
        if(CollectionUtils.isEmpty(menus)){

            menus = menuMapper.getMenusByAdminId(adminId);
            //将菜单数据设置入Redis中
            valueOperations.set("menu_"+adminId,menus);

        }
        return menus;




    }
}
