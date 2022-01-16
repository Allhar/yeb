package com.allhar.server.mapper;

import com.allhar.server.pojo.Admin;
import com.allhar.server.pojo.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

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
     * 获取所有操作员
     * @return
     */
    List<Admin> getAllAdmins(@Param("id") Integer id, @Param("keyWords") String keyWords);
}
