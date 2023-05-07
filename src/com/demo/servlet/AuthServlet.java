package com.demo.servlet;

import com.alibaba.fastjson.JSON;
import com.demo.service.FoodService;
import com.demo.service.NoticeService;
import com.demo.service.UserService;
import com.demo.service.impl.FoodServiceImpl;
import com.demo.service.impl.NoticeServiceImpl;
import com.demo.service.impl.UserServiceImpl;
import com.demo.util.Util;
import com.demo.vo.Food;
import com.demo.vo.Notice;
import com.demo.vo.User;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

//@WebServlet("/AuthServlet")
public class AuthServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //过滤编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = Util.decode(request, "action");
       if ("login".equalsIgnoreCase(action)) {//登录

            String username = Util.decode(request, "username");
            String password = Util.decode(request, "password");


            String validationCode = Util.decode(request, "validationCode");
            if (validationCode != null && !validationCode.equals(request.getSession().getAttribute("validationCode"))) {//验证码不通过
                request.getSession().setAttribute("alert_msg", "err:code!");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }

            UserService userService = new UserServiceImpl();
            Map<String, Object> params = new HashMap();
            params.put("searchColumn", "username");//使用`username`字段进行模糊查询
            params.put("keyword", username);
            List<User> list = (List<User>) userService.list(params).get("list");
            for (User user : list) {
                if (user.getUsername().equals(username)
                        && user.getPassword().equals(password)) {//找到这个管理员了
                    request.getSession().setAttribute("loginUser", user);
                    request.getSession().setAttribute("noticeList", JSON.toJSONString(getNoticeList(user)));
                    String forwardPage = Util.decode(request, "forwardPage");
                    if (("user").equals(user.getUserType())) {
                        forwardPage = "food_list.jsp";
                    }
                    request.getRequestDispatcher(forwardPage).forward(request, response);
                    return;
                }
            }

            request.getSession().setAttribute("alert_msg", "err:username or password！");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else if ("register".equalsIgnoreCase(action)) {//注册
            String username = Util.decode(request, "username");
            String password = Util.decode(request, "password");
            System.out.println("username=" + username);
            System.out.println("password=" + password);
            UserService userService = new UserServiceImpl();
            Map<String, Object> params = new HashMap();
            params.put("searchColumn", "username");//使用`username`字段进行模糊查询
            params.put("keyword", username);
            params.put("startIndex", 0);
            params.put("pageSize", Long.MAX_VALUE);
            List<User> list = (List<User>) userService.list(params).get("list");
            for (User user : list) {
                if (user.getUsername().equals(username) /*&& user.getPassword().equals(password)*/) {       //说明该用户名已存在，必须换个用户名才能注册
                    request.getSession().setAttribute("alert_msg", "err:username is existence！");
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                    return;
                }
            }
            User vo = new User();
            vo.setUsername(username);
            vo.setPassword(password);
            vo.setUserType("user");
            userService.add(vo);
            request.getSession().setAttribute("alert_msg", "register success：[" + username + "]");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else if ("logout".equalsIgnoreCase(action)) {//登出
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("loginUser");
            if (user != null) {
                session.removeAttribute("loginUser");
            }
            response.sendRedirect("login.jsp");
        } else if ("validationCode".equalsIgnoreCase(action)) {
            String codeChars = "0123456789";                                                                                                                // 图形验证码的字符集合，系统将随机从这个字符串中选择一些字符作为验证码
                                                                                                                                                           //  获得验证码集合的长度
            int charsLength = codeChars.length();
                                                                                                                                                           //  下面三条记录是关闭客户端浏览器的缓冲区
                                                                                                                                                            //  这三条语句都可以关闭浏览器的缓冲区，但是由于浏览器的版本不同，对这三条语句的支持也不同
                                                                                                                                                             //  因此，为了保险起见，建议同时使用这三条语句来关闭浏览器的缓冲区
            response.setHeader("ragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
                                                                                                                                                                //  设置图形验证码的长和宽（图形的大小）
            int width = 90, height = 20;
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = image.getGraphics();                                                                                                                 //  获得用于输出文字的Graphics对象
            Random random = new Random();
            g.setColor(Util.getRandomColor(180, 250));                                                                                                        // 随机设置要填充的颜色
            g.fillRect(0, 0, width, height);                                                                                                             //  填充图形背景
                                                                                                                                                             //  设置初始字体
            g.setFont(new Font("Times New Roman", Font.ITALIC, height));
            g.setColor(Util.getRandomColor(120, 180));                                                                                                     // 随机设置字体颜色
                                                                                                                                                             //  用于保存最后随机生成的验证码
            StringBuilder validationCode = new StringBuilder();
                                                                                                                                                             //  验证码的随机字体
            String[] fontNames = {"Times New Roman", "Book antiqua", "Arial"};
            for (int i = 0; i < 4; i++) {
                                                                                                                                                                  //  随机设置当前验证码的字符的字体
                g.setFont(new Font(fontNames[random.nextInt(3)], Font.ITALIC, height));
                                                                                                                                                                    //  随机获得当前验证码的字符
                char codeChar = codeChars.charAt(random.nextInt(charsLength));
                validationCode.append(codeChar);
                                                                                                                                                                //  随机设置当前验证码字符的颜色
                g.setColor(Util.getRandomColor(10, 100));
                                                                                                                                                              //  在图形上输出验证码字符，x和y都是随机生成的
                g.drawString(String.valueOf(codeChar), 16 * i + random.nextInt(7), height - random.nextInt(6));
            }
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(5 * 60);
                                                                                                                                                                //  将验证码保存在session对象中，key为validation_code
            session.setAttribute("validationCode", validationCode.toString());
            g.dispose();                                                                                                                                        //  关闭Graphics对象
            OutputStream os = response.getOutputStream();
            ImageIO.write(image, "JPEG", os);                                                                                                       // 以JPEG格式向客户端发送图形验证码
        } else if ("resetPassword".equalsIgnoreCase(action)) {
            String msg;
            User loginUser = (User) request.getSession().getAttribute("loginUser");
            String oldPassword = Util.decode(request, "oldPassword");
            if (!loginUser.getPassword().equals(oldPassword)) {
                msg = "password err！";
            } else {
                String newPassword = Util.decode(request, "newPassword");
                loginUser.setPassword(newPassword);
                UserService userService = new UserServiceImpl();
                userService.update(loginUser);
                msg = "update success！";
            }
            request.getSession().setAttribute("alert_msg", msg);
            request.getRequestDispatcher("reset_password.jsp").forward(request, response);
        } else {
            response.sendRedirect("login.jsp");
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
        doPost(request, response);
    }

    private List<Notice> getNoticeList(User user){
        NoticeService noticeService = new NoticeServiceImpl();
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> noticeMap = noticeService.list(params, user.getId());
        List<Notice> noticeList = (List<Notice>)noticeMap.get("list");

        FoodService foodService = new FoodServiceImpl();
        Map<String, Object> foodMap = foodService.list(params, null);
        List<Food> foodList = (List<Food>)foodMap.get("list");

        List<Notice> noticeList1 = new ArrayList<>();
        if ((foodList != null && !foodList.isEmpty())
                && (noticeList != null && !noticeList.isEmpty())) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            for (Notice notice : noticeList) {
                try {
                    Date date = format.parse(notice.getNoticeDate());
                    boolean b = date.before(new Date());
                    if(b){
                        //查找提醒信息是否有过期食品
                        for (Food food : foodList) {
                            String noticeName = notice.getNoticeName();
                            if (food.getFoodName().equals(noticeName)){
                                noticeList1.add(notice);
                            }
                        }

                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }


        }
        return noticeList1;
    }
}
