package com.demo.servlet;

import com.demo.util.Util;
import com.demo.service.FoodService;
import com.demo.service.impl.FoodServiceImpl;
import com.demo.vo.Food;
import com.demo.vo.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class FoodServlet extends HttpServlet {

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
        String action = Util.decode(request, "action");
        if ("add".equals(action)) {//增加
            Food vo = new Food();

            String foodName = Util.decode(request, "foodName");
            if (foodName != null && !foodName.equals("")) {
                vo.setFoodName(foodName);
            }
            String foodLan = Util.decode(request, "foodLan");
            if (foodLan != null && !foodLan.equals("")) {
                vo.setFoodLan(foodLan);
            }
            String foodTing = Util.decode(request, "foodTing");
            if (foodTing != null && !foodTing.equals("")) {
                vo.setFoodTing(foodTing);
            }
            String foodDate = Util.decode(request, "foodDate");
            if (foodDate != null && !foodDate.equals("")) {
                vo.setFoodDate(foodDate);
            }
            String foodLong = Util.decode(request, "foodLong");
            if (foodLong != null && !foodLong.equals("")) {
                vo.setFoodLong(foodLong);
            }
            String foodPrice = Util.decode(request, "foodPrice");
            if (foodPrice != null && !foodPrice.equals("")) {
                vo.setFoodPrice(foodPrice);
            }
            String foodText = Util.decode(request, "foodText");
            if (foodText != null && !foodText.equals("")) {
                vo.setFoodText(foodText);
            }
            FoodService foodService = new FoodServiceImpl();
            //调用Service层增加方法（add），增加记录
            foodService.add(vo);
            this.redirectList(request, response);
        } else if ("delete".equals(action)) {//删除
            //取出表要删除的记录的主键
            long id = Long.parseLong(Util.decode(request, "id"));
            FoodService foodService = new FoodServiceImpl();
            //调用Service层删除方法（delete），将对应的记录删除
            foodService.delete(id);
            this.redirectList(request, response);
        } else if ("edit".equals(action)) {//修改

            Food vo = new Food();
            String id = Util.decode(request, "id");
            if (id != null && !id.equals("")) {
                vo.setId(Long.valueOf(id));
            }
            String foodName = Util.decode(request, "foodName");
            if (foodName != null && !foodName.equals("")) {
                vo.setFoodName(foodName);
            }
            String foodLan = Util.decode(request, "foodLan");
            if (foodLan != null && !foodLan.equals("")) {
                vo.setFoodLan(foodLan);
            }
            String foodTing = Util.decode(request, "foodTing");
            if (foodTing != null && !foodTing.equals("")) {
                vo.setFoodTing(foodTing);
            }
            String foodDate = Util.decode(request, "foodDate");
            if (foodDate != null && !foodDate.equals("")) {
                vo.setFoodDate(foodDate);
            }
            String foodLong = Util.decode(request, "foodLong");
            if (foodLong != null && !foodLong.equals("")) {
                vo.setFoodLong(foodLong);
            }
            String foodPrice = Util.decode(request, "foodPrice");
            if (foodPrice != null && !foodPrice.equals("")) {
                vo.setFoodPrice(foodPrice);
            }
            String foodText = Util.decode(request, "foodText");
            if (foodText != null && !foodText.equals("")) {
                vo.setFoodText(foodText);
            }
            FoodService foodService = new FoodServiceImpl();
            //调用Service层更新方法（update），更新记录
            foodService.update(vo);
            this.redirectList(request, response);
        } else if ("get".equalsIgnoreCase(action) || "editPre".equalsIgnoreCase(action)) {//根据主键ID，查询详情信息并跳转到详情页面或编辑页面
            Serializable id = Util.decode(request, "id");//取出页面传入的主键，用于查询详情
            FoodService foodService = new FoodServiceImpl();
            Food vo = foodService.get(id);
            request.getSession().setAttribute("vo", vo);
            String to = "get".equalsIgnoreCase(action) ? "info" : "edit";//判断是去详情显示页面还是编辑页面
            response.sendRedirect("food_" + to + ".jsp");
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
        String searchColumn = Util.decode(request, "searchColumn");
        String keyword = Util.decode(request, "keyword");
        Map<String, Object> params = new HashMap();//用来保存控制层传进来的参数(查询条件)
        params.put("searchColumn", searchColumn);//要查询的列
        params.put("keyword", keyword);//查询的关键字
        HttpSession session = request.getSession();
        User loginUser = (User)session.getAttribute("loginUser");
        String username = loginUser.getUsername();
        if ("admin".equals(loginUser.getUserType())){
            username = null;
        }
        FoodService foodService = new FoodServiceImpl();
        response.getWriter().println(com.alibaba.fastjson.JSONObject.toJSONString(foodService.list(params, null).get("list")));
    }
}
