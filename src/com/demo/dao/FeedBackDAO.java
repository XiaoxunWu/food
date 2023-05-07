package com.demo.dao;

import com.demo.vo.Feedback;

import java.io.Serializable;
import java.util.Map;


public interface FeedBackDAO {
    /**
     * 增加反馈记录
     *
     * @param vo
     * @return
     */
    void add(Feedback vo);

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
    void update(Feedback vo);

    /**
     * 根据主键id获取记录的详情
     *
     * @param id
     * @return
     */
    Feedback get(Serializable id);

    /**
     * 根据条件查询列表与数量
     *
     * @param params
     * @return
     */
    Map<String, Object> list(Map<String, Object> params, Long userId);

    void updateStatus(Feedback vo);
}
