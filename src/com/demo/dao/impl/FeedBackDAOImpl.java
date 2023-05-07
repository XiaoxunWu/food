package com.demo.dao.impl;

import com.demo.dao.FeedBackDAO;
import com.demo.util.Util;
import com.demo.vo.Feedback;
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

public class FeedBackDAOImpl implements FeedBackDAO {

    //@Override
    public void add(Feedback vo) {
        Connection connection = null;
        try {
            QueryRunner queryRunner = new QueryRunner();
            connection = Util.getConnection();
            String sql = "insert into `t_feedback` (`food_name`,`content`,`user_id`, create_time, update_time, status) values(?,?,?,?,?,?)";
            queryRunner.update(connection,sql,vo.getFoodName(), vo.getContent(),vo.getUserId(),vo.getCreateTime(),vo.getUpdateTime(),vo.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    //@Override
    public void update(Feedback vo) {
        Connection connection = null;
        try {
            QueryRunner queryRunner = new QueryRunner();
            connection = Util.getConnection();
            String sql = "update `t_feedback` set `food_name` = ? ,`content` = ? ,`user_id` = ?, `update_time` = ? where `id` = ?";
            queryRunner.update(connection,sql,vo.getFoodName(), vo.getContent(), vo.getUserId(), vo.getUpdateTime(),vo.getId());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    public void updateStatus(Feedback vo) {
        Connection connection = null;
        try {
            QueryRunner queryRunner = new QueryRunner();
            connection = Util.getConnection();
            String sql = "update `t_feedback` set `status` = 'Done'  where `id` = ?";
            queryRunner.update(connection,sql,vo.getId());
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
            String sql = "delete from `t_feedback` where id = ?";
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
    public Feedback get(Serializable id) {
        Feedback vo = null;
        Connection c = null;
        Statement s = null;
        ResultSet rs = null;
        try {
            c = Util.getConnection();
            s = c.createStatement();
            String sql = "select f.*, u.username from `t_feedback` f " +
                         "left join t_user u on u.id = f.user_id " +
                          "where f.id = " + id;
            rs = s.executeQuery(sql);
            if (rs.next()) {
                vo = new Feedback();
                vo.setId(rs.getLong("id"));
                vo.setUserId(rs.getLong("user_id"));
                vo.setContent(rs.getString("content"));
                vo.setCreateTime(Util.getTime(rs.getTimestamp("create_time")));
                vo.setUpdateTime(Util.getTime(rs.getTimestamp("update_time")));
                vo.setUsername(rs.getString("username"));
                vo.setFoodName(rs.getString("food_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(c, s, rs);
            return vo;
        }
    }

    //@Override
    public Map<String, Object> list(Map<String, Object> params, Long userId) {
        List<Feedback> list = new ArrayList();
        int totalCount = 0;
        String condition = "";
        String sqlList;
        if (params.get("searchColumn") != null && !"".equals(params.get("searchColumn"))) {
            condition += " and f.`" + params.get("searchColumn") + "` like '%" + params.get("keyword") + "%'";
        }
        if (userId != null) {
            condition += "and f.`user_id` = " + userId;
        }
        try {
            Connection c = Util.getConnection();
            PreparedStatement ps;
            ResultSet rs;
            String limit = (params.get("startIndex") != null && params.get("pageSize") != null) ? " limit " + params.get("startIndex") + "," + params.get("pageSize") : "";
            sqlList = "select f.*, u.username from `t_feedback` f " +
                    "left join t_user u on u.id = f.user_id " +
                    "where 1=1 " + condition + " order by id asc " + limit + ";";
            ps = c.prepareStatement(sqlList);
            rs = ps.executeQuery();
            while (rs.next()) {
                Feedback vo = new Feedback();
                vo.setId(rs.getLong("id"));
                vo.setUserId(rs.getLong("user_id"));
                vo.setContent(rs.getString("content"));
                vo.setCreateTime(Util.getTime(rs.getTimestamp("create_time")));
                vo.setUpdateTime(Util.getTime(rs.getTimestamp("update_time")));
                vo.setUsername(rs.getString("username"));
                vo.setFoodName(rs.getString("food_name"));
                vo.setStatus(rs.getString("status"));
                list.add(vo);
            }
            String sqlCount = "select count(*) from `t_feedback` f " +
                              "left join t_user u on u.id = f.user_id " +
                               " where 1=1 " + condition;
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
