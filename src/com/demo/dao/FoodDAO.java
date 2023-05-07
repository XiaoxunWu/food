package com.demo.dao;

import com.demo.vo.Food;

import java.io.Serializable;
import java.util.Map;


public interface FoodDAO {
    /**
     * 增加记录
     *
     * @param vo
     * @return
     */
    void add(Food vo);

    /**
     * 根据主键id，删除对应的记录
     *
     * @param id
     * @return
     */
    boolean delete(long id);

    /**
     * 更新记录
     *
     * @param vo
     * @return
     */
    void update(Food vo);

    /**
     * 根据主键id获取记录的详情
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
