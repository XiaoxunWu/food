package com.demo.service.impl;

import com.demo.dao.FeedBackDAO;
import com.demo.dao.impl.FeedBackDAOImpl;
import com.demo.service.FeedbackService;
import com.demo.vo.Feedback;

import java.io.Serializable;
import java.util.Map;

/**
 * feedback模块的Service层（业务层）的具体实现类，对 feedbackService接口中定义的抽象方法作出具体的功能实现
 */
public class FeedbackServiceImpl implements FeedbackService {
    //@Override
    public void add(Feedback vo) {
        FeedBackDAO feedBackDAO = new FeedBackDAOImpl();
        feedBackDAO.add(vo);
    }

    //@Override
    public void delete(long id) {
        FeedBackDAO feedBackDAO = new FeedBackDAOImpl();
        feedBackDAO.delete(id);
    }

    //@Override
    public void update(Feedback vo) {
        FeedBackDAO feedBackDAO = new FeedBackDAOImpl();
        feedBackDAO.update(vo);
    }

    public void updateStatus(Feedback vo) {
        FeedBackDAO feedBackDAO = new FeedBackDAOImpl();
        feedBackDAO.updateStatus(vo);
    }

    //@Override
    public Feedback get(Serializable id) {
        FeedBackDAO feedBackDAO = new FeedBackDAOImpl();
        return feedBackDAO.get(id);
    }

    //@Override
    public Map<String, Object> list(Map<String, Object> params, Long userId) {
        FeedBackDAO feedBackDAO = new FeedBackDAOImpl();
        return feedBackDAO.list(params, userId);
    }
}
