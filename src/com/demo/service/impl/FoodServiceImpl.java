package com.demo.service.impl;

import com.demo.dao.FoodDAO;
import com.demo.dao.impl.FoodDAOImpl;
import com.demo.service.FoodService;
import com.demo.vo.Food;

import java.io.Serializable;
import java.util.Map;

/**
 * food模块的Service层（业务层）的具体实现类，对foodService接口中定义的抽象方法作出具体的功能实现
 */
public class FoodServiceImpl implements FoodService {
    //@Override
    public void add(Food vo) {
        FoodDAO foodDAO = new FoodDAOImpl();
        foodDAO.add(vo);
    }

    //@Override
    public void delete(long id) {
        FoodDAO foodDAO = new FoodDAOImpl();
        foodDAO.delete(id);
    }

    //@Override
    public void update(Food vo) {
        FoodDAO foodDAO = new FoodDAOImpl();
        foodDAO.update(vo);
    }

    //@Override
    public Food get(Serializable id) {
        FoodDAO foodDAO = new FoodDAOImpl();
        return foodDAO.get(id);
    }

    //@Override
    public Map<String, Object> list(Map<String, Object> params, String username) {
        FoodDAO foodDAO = new FoodDAOImpl();
        return foodDAO.list(params, username);
    }
}
