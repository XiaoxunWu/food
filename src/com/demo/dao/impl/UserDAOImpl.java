package com.demo.dao.impl;

import com.demo.util.Util;
import com.demo.dao.UserDAO;
import com.demo.vo.User;
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

/**
 * User模块的DAO层（数据层）的具体实现类，对UserDAO接口中定义的增删改查等抽象方法作出具体的功能实现
 */
public class UserDAOImpl implements UserDAO {

    //@Override
    public void add(User vo) {
        Connection connection = null;
        try {
            QueryRunner queryRunner = new QueryRunner();
            connection = Util.getConnection();
            String sql = "insert into `t_user` (`username`,`password`,`real_name`,`user_sex`,`user_phone`,`user_text`,`user_type`) values(?,?,?,?,?,?,?)";
            queryRunner.update(connection,sql,vo.getUsername(),vo.getPassword(), vo.getRealName(), vo.getUserSex(), vo.getUserPhone(), vo.getUserText(), vo.getUserType());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    //@Override
    public void update(User vo) {
        Connection connection = null;
        try {
            QueryRunner queryRunner = new QueryRunner();
            connection = Util.getConnection();
            String sql = "update `t_user` set `username` = ? ,`password` = ? ,`real_name` = ? ,`user_sex` = ? ,`user_phone` = ? ,`user_text` = ? ,`user_type` = ?  where `id` = ?";
            queryRunner.update(connection,sql,vo.getUsername(),vo.getPassword(), vo.getRealName(), vo.getUserSex(), vo.getUserPhone(), vo.getUserText(), vo.getUserType(),vo.getId());
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
            String sql = "delete from `t_user` where id = ?" ;
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
    public User get(Serializable id) {
        User vo = null;
        Connection c = null;
        Statement s = null;
        ResultSet rs = null;
        try {
            c = Util.getConnection();
            s = c.createStatement();
            String sql = "select * from `t_user` where id = " + id;
            rs = s.executeQuery(sql);
            if (rs.next()) {
                vo = new User();
                vo.setId(rs.getLong("id"));
                vo.setUsername(rs.getString("username"));
                vo.setPassword(rs.getString("password"));
                vo.setRealName(rs.getString("real_name"));
                vo.setUserSex(rs.getString("user_sex"));
                vo.setUserPhone(rs.getString("user_phone"));
                vo.setUserText(rs.getString("user_text"));
                vo.setUserType(rs.getString("user_type"));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            DbUtils.closeQuietly(c,s,rs);
            return vo;
        }
    }

    //@Override
    public Map<String, Object> list(Map<String, Object> params) {
        List<User> list = new ArrayList();
        int totalCount = 0;
        String condition = "";
        String sqlList;
        if (params.get("searchColumn") != null && !"".equals(params.get("searchColumn"))) {
            condition += " and `" + params.get("searchColumn") + "` like '%" + params.get("keyword") + "%'";
        }
        try {
            Connection c = Util.getConnection();
            PreparedStatement ps;
            ResultSet rs;
            String limit = (params.get("startIndex") != null && params.get("pageSize") != null) ? " limit " + params.get("startIndex") + "," + params.get("pageSize") : "";
            sqlList = "select * from `t_user` where 1=1 " + condition + " order by id asc " + limit + ";";
            ps = c.prepareStatement(sqlList);
            rs = ps.executeQuery();
            while (rs.next()) {
                User vo = new User();
                vo.setId(rs.getLong("id"));
                vo.setUsername(rs.getString("username"));
                vo.setPassword(rs.getString("password"));
                vo.setRealName(rs.getString("real_name"));
                vo.setUserSex(rs.getString("user_sex"));
                vo.setUserPhone(rs.getString("user_phone"));
                vo.setUserText(rs.getString("user_text"));
                vo.setUserType(rs.getString("user_type"));
                list.add(vo);
            }
            String sqlCount = "select count(*) from `t_user` where 1=1 " + condition;
            ps = c.prepareStatement(sqlCount);
            rs = ps.executeQuery();
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }
            DbUtils.closeQuietly(c,ps,rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> result = new HashMap();
        result.put("list", list);
        result.put("totalCount", totalCount);
        return result;
    }
}
