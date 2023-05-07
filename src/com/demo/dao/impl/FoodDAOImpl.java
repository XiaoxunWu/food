package com.demo.dao.impl;

import com.demo.util.Util;
import com.demo.dao.FoodDAO;
import com.demo.vo.Food;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodDAOImpl implements FoodDAO {

    //@Override
    public void add(Food vo) {
        Connection connection = null;
        try {
            QueryRunner queryRunner = new QueryRunner();
            connection = Util.getConnection();
            String sql = "insert into `t_food` (`food_name`,`food_lan`,`food_ting`,`food_date`,`food_long`,`food_price`,`food_text`) values(?,?,?,?,?,?,?)";
            queryRunner.update(connection,sql,vo.getFoodName(), vo.getFoodLan(), vo.getFoodTing(), vo.getFoodDate(), vo.getFoodLong(), vo.getFoodPrice(), vo.getFoodText());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    //@Override
    public void update(Food vo) {
        Connection connection = null;
        try {
            QueryRunner queryRunner = new QueryRunner();
            connection = Util.getConnection();
            String sql = "update `t_food` set `food_name` = ? ,`food_lan` = ? ,`food_ting` = ? ,`food_date` = ? ,`food_long` = ? ,`food_price` = ? ,`food_text` = ?  where `id` = ?";
            queryRunner.update(connection,sql,vo.getFoodName(), vo.getFoodLan(), vo.getFoodTing(), vo.getFoodDate(), vo.getFoodLong(), vo.getFoodPrice(), vo.getFoodText(),vo.getId());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    //@Override
    public boolean delete(long id) {
        Connection connection = null;
        try {
            QueryRunner queryRunner = new QueryRunner();
            connection = Util.getConnection();
            String sql = "delete from `t_food` where id = ?";
            queryRunner.update(connection,sql,id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DbUtils.closeQuietly(connection);
            return true;
        }
    }

    //@Override
    public Food get(Serializable id) {
        Food vo = null;
        Connection c = null;
        Statement s = null;
        ResultSet rs = null;
        try {
            c = Util.getConnection();
            s = c.createStatement();
            String sql = "select * from `t_food` where id = " + id;
            rs = s.executeQuery(sql);
            if (rs.next()) {
                vo = new Food();
                vo.setId(rs.getLong("id"));
                vo.setFoodName(rs.getString("food_name"));
                vo.setFoodLan(rs.getString("food_lan"));
                vo.setFoodTing(rs.getString("food_ting"));
                vo.setFoodDate(rs.getString("food_date"));
                vo.setFoodLong(rs.getString("food_long"));
                vo.setFoodPrice(rs.getString("food_price"));
                vo.setFoodText(rs.getString("food_text"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(c, s, rs);
            return vo;
        }
    }

    //@Override
    public Map<String, Object> list(Map<String, Object> params, String username) {
        List<Food> list = new ArrayList();
        int totalCount = 0;
        String condition = "";
        String sqlList;
        if (params.get("searchColumn") != null && !"".equals(params.get("searchColumn"))) {
            condition += " and `" + params.get("searchColumn") + "` like '%" + params.get("keyword") + "%'";
        }
        if (username != null) {
            condition += "and `food_ting` = \"" + username + "\"";
        }
        try {
            Connection c = Util.getConnection();
            PreparedStatement ps;
            ResultSet rs;
            String limit = (params.get("startIndex") != null && params.get("pageSize") != null) ? " limit " + params.get("startIndex") + "," + params.get("pageSize") : "";
            sqlList = "select * from `t_food` where 1=1 " + condition + " order by id asc " + limit + ";";
            ps = c.prepareStatement(sqlList);
            rs = ps.executeQuery();
            while (rs.next()) {
                Food vo = new Food();
                vo.setId(rs.getLong("id"));
                vo.setFoodName(rs.getString("food_name"));
                vo.setFoodLan(rs.getString("food_lan"));
                vo.setFoodTing(rs.getString("food_ting"));
                vo.setFoodDate(rs.getString("food_date"));
                vo.setFoodLong(rs.getString("food_long"));
                vo.setFoodPrice(rs.getString("food_price"));
                vo.setFoodText(rs.getString("food_text"));
                list.add(vo);
            }
            String sqlCount = "select count(*) from `t_food` where 1=1 " + condition;
            ps = c.prepareStatement(sqlCount);
            rs = ps.executeQuery();
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }
            DbUtils.closeQuietly(c, ps, rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> result = new HashMap();
        result.put("list", list);
        result.put("totalCount", totalCount);
        return result;
    }
}
