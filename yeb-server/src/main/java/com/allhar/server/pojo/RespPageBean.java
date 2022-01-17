package com.allhar.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * RespPageBean
 * 公共返回分页对象
 *
 * @author allhar
 * @since 1.0.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespPageBean {

    /**
     * 总条数
     */
    private Long total;

    /**
     * 数据List
     */
    private List<?> data;


}
