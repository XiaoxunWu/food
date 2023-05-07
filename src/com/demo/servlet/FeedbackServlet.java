package com.demo.servlet;

import com.demo.service.FeedbackService;
import com.demo.service.impl.FeedbackServiceImpl;
import com.demo.util.Util;
import com.demo.vo.Feedback;
import com.demo.vo.User;
import com.mysql.cj.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Notice模块的Servlet控制层，负责接收页面传过来的请求参数，根据action参数的值来确定页面要执行的具体操作<br>
 * 而后再调用NoticeService业务层的方法来处理具体的业务，最后将处理完成的结果返回或跳转至相应页面
 */
//@WebServlet("/FeedbackServlet")
public class FeedbackServlet extends HttpServlet {

    /**
     * 处理Post请求
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //过滤编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        User loginUser = (User)session.getAttribute("loginUser");
        String action = Util.decode(request, "action");
        if ("add".equals(action)) {//增加
            Feedback vo = new Feedback();
            //取出页面传进来的各个数据，并设置到Notice对象的属性里
            String foodName = Util.decode(request, "foodName");
            vo.setFoodName(foodName);

            String content = Util.decode(request, "content");
            if (!StringUtils.isNullOrEmpty(content)) {
                vo.setContent(content);
            }
            String time = Util.getTime(new Date());
            vo.setCreateTime(time);
            vo.setUpdateTime(time);
            vo.setUserId(loginUser.getId());
            vo.setStatus("dealing");

            FeedbackService feedbackService = new FeedbackServiceImpl();
            //调用Service层增加方法（add），增加记录
            feedbackService.add(vo);
            this.redirectList(request, response);
        } else if ("delete".equals(action)) {//删除
            //取出表要删除的公告记录的主键
            long id = Long.parseLong(Util.decode(request, "id"));
            FeedbackService feedbackService = new FeedbackServiceImpl();
            //调用Service层删除方法（delete），将对应的记录删除
            feedbackService.delete(id);
            this.redirectList(request, response);
        } else if ("edit".equals(action)) {//修改
            //取出页面传进来的各个数据，并设置到Notice对象的属性里
            Feedback vo = new Feedback();
            //取出页面传进来的各个数据，并设置到Notice对象的属性里
            vo.setId(Long.parseLong( Util.decode(request, "id")));
            String foodName = Util.decode(request, "foodName");
            vo.setFoodName(foodName);

            String time = Util.getTime(new Date());
            vo.setUpdateTime(time);

            String content = Util.decode(request, "content");
            if (!StringUtils.isNullOrEmpty(content)) {
                vo.setContent(content);
            }
            vo.setUserId(loginUser.getId());

            FeedbackService feedbackService = new FeedbackServiceImpl();
            //调用Service层增加方法（add），增加记录
            feedbackService.update(vo);
            this.redirectList(request, response);
        }else if ("deal".equals(action)) {//处理
            long id = Long.parseLong(Util.decode(request, "id"));
            FeedbackService feedbackService = new FeedbackServiceImpl();
            Feedback vo = new Feedback();
            vo.setId(id);
            feedbackService.updateStatus(vo);
            this.redirectList(request, response);
        } else if ("get".equalsIgnoreCase(action) || "editPre".equalsIgnoreCase(action)) {//根据主键ID，查询详情信息并跳转到详情页面或编辑页面
            Serializable id = Util.decode(request, "id");//取出页面传入的主键，用于查询详情
            FeedbackService feedbackService = new FeedbackServiceImpl();
            Feedback vo = feedbackService.get(id);
            session.setAttribute("vo", vo);
            String to = "get".equalsIgnoreCase(action) ? "info" : "edit";//判断是去详情显示页面还是编辑页面
            response.sendRedirect("feedback_" + to + ".jsp");
        } else {//默认去列表页面
            this.redirectList(request, response);
        }
    }

    /**
     * 处理Get请求
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);//Get请求和Post请求的处理是一样的，所以把request、response转交给Post方法就好
    }

    /**
     * 根据参数，查询出条例条件的记录集合，最后将数据返回给调用处或者将数据集合设置到session域里，再跳转到对应的列表页面
     *
     * @param request
     * @param response
     */
    private void redirectList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //查询列和关键字
        HttpSession session = request.getSession();
        User loginUser = (User)session.getAttribute("loginUser");
        Long userId = loginUser.getId();
        if ("admin".equals(loginUser.getUserType())){
            userId = null;
        }
        String searchColumn = Util.decode(request, "searchColumn");
        String keyword = Util.decode(request, "keyword");
        Map<String, Object> params = new HashMap();//用来保存控制层传进来的参数(查询条件)
        params.put("searchColumn", searchColumn);//要查询的列
        params.put("keyword", keyword);//查询的关键字
        FeedbackService feedbackService = new FeedbackServiceImpl();
        response.getWriter().println(com.alibaba.fastjson.JSONObject.toJSONString(feedbackService.list(params, userId).get("list")));
    }
}
