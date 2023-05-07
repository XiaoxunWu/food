package com.demo.dao.impl;

import com.demo.util.Util;
import com.demo.dao.NoticeDAO;
import com.demo.vo.Notice;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Notice模块的DAO层（数据层）的具体实现类，对NoticeDAO接口中定义的增删改查等抽象方法作出具体的功能实现
 */
public class NoticeDAOImpl implements NoticeDAO {
    //@Override
    public void add(Notice vo) {
        Connection connection = null;
        try {
            QueryRunner queryRunner = new QueryRunner();
            connection = Util.getConnection();
            String sql = "insert into `t_notice` (`notice_name`,`notice_text`,`notice_date`, `user_id`) values(?,?,?,?)";
            queryRunner.update(connection,sql,vo.getNoticeName(),vo.getNoticeText(),vo.getNoticeDate(),vo.getUseId());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    //@Override
    public void update(Notice vo) {
        Connection connection = null;
        try {
            QueryRunner queryRunner = new QueryRunner();
            connection = Util.getConnection();
            String sql = "update `t_notice` set `notice_name` = ? ,`notice_text` = ? ,`notice_date` = ?  where `id` = ?";
            queryRunner.update(connection,sql,vo.getNoticeName(),vo.getNoticeText(),vo.getNoticeDate(),vo.getId());
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
            String sql = "delete from `t_notice` where id = ?";
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
    public Notice get(Serializable id) {
        Notice vo = null;
        Connection c = null;
        Statement s = null;

        ResultSet re = null;//返回结果集
        try {
            c = Util.getConnection();
            s = c.createStatement();
            String sql = "select * from `t_notice` where id = " + id;
            re = s.executeQuery(sql);
            if (re.next()) {
                vo = new Notice();
                vo.setId(re.getLong("id"));
                vo.setNoticeName(re.getString("notice_name"));
                vo.setNoticeText(re.getString("notice_text"));
                vo.setNoticeDate(re.getString("notice_date"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(c, s, re);
            return vo;
        }

    }

    //@Override
    public Map<String, Object> list(Map<String, Object> params, Long userId) {
        List<Notice> list = new ArrayList();
        int totalCount = 0;
        String condition = "";
        String sqlList;
        if (params.get("searchColumn") != null && !"".equals(params.get("searchColumn"))) {
            condition += " and `" + params.get("searchColumn") + "` like '%" + params.get("keyword") + "%'";
        }
        if (userId != null) {
            condition += "and `user_id` = " + userId;
        }
        try {
            Connection c = Util.getConnection();
            PreparedStatement ps;
            ResultSet rs;
            String limit = (params.get("startIndex") != null && params.get("pageSize") != null) ? " limit " + params.get("startIndex") + "," + params.get("pageSize") : "";
            sqlList = "select * from `t_notice` where 1=1 " + condition + " order by id asc " + limit + ";";
            ps = c.prepareStatement(sqlList);
            rs = ps.executeQuery();
            while (rs.next()) {
                Notice vo = new Notice();
                vo.setId(rs.getLong("id"));
                vo.setNoticeName(rs.getString("notice_name"));
                vo.setNoticeText(rs.getString("notice_text"));
                vo.setNoticeDate(rs.getString("notice_date"));
                list.add(vo);
            }
            String sqlCount = "select count(*) from `t_notice` where 1=1 " + condition;
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
