package com.demo.service;

import com.demo.vo.Food;

import java.io.Serializable;
import java.util.Map;

/**
 * food模块的Service层（业务层）接口，提供业务方法的抽象
 */
public interface FoodService {
    /**
     * 增加
     *
     * @param vo
     * @return
     */
    void add(Food vo);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    void delete(long id);

    /**
     * 修改
     *
     * @param vo
     * @return
     */
    void update(Food vo);

    /**
     * 根据主键Id查询详情
     *
     * @param id
     * @return
     */
    Food get(Serializable id);

    /**
     * 根据条件查询列表与数量
     *
     * @param params
     * @return
     */
    Map<String, Object> list(Map<String, Object> params, String username);
}
