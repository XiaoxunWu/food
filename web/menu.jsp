<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${loginUser.userType == 'admin'}">
<li class="layui-nav-item <%=request.getParameter("User_active")%>"><a href="user_list.jsp">USER</a></li>
</c:if>
<li class="layui-nav-item <%=request.getParameter("Food_active")%>"><a href="food_list.jsp">FOOD</a></li>
<c:if test="${loginUser.userType == 'user'}">
<li class="layui-nav-item <%=request.getParameter("Notice_active")%>"><a href="notice_list.jsp">NOTICE</a></li>
</c:if>
<li class="layui-nav-item <%=request.getParameter("Feedback_active")%>"><a href="feedback_list.jsp">FEEDBACK</a></li>
