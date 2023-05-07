<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>userInfo</title>
    <script src="js/jquery.js"></script>
    <link rel="stylesheet" href="js/layui/css/layui.css">
</head>
<body style="padding: 20px">
<form class="layui-form" action="">
    <div class="layui-form-item">
        <label class="layui-form-label">UserName：</label>
        <div class="layui-input-block" style="padding: 9px 15px;">
            ${vo.username}
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">Password：</label>
        <div class="layui-input-block" style="padding: 9px 15px;">
            ${vo.realName}
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">Gender：</label>
        <div class="layui-input-block">
            <input name="userSex" type="radio" value="male" ${vo.userSex=='male'?'checked':''} class="layui-input" title="male" disabled/>
            <input name="userSex" type="radio" value="female" ${vo.userSex=='female'?'checked':''} class="layui-input" title="female" disabled/>
            <input name="userSex" type="radio" value="other" ${vo.userSex=='other'?'checked':''} class="layui-input" title="other" disabled/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">Phone：</label>
        <div class="layui-input-block" style="padding: 9px 15px;">
            ${vo.userPhone}
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">Text：</label>
        <div class="layui-input-block">
            <textarea type="text" name="userText" disabled="disabled" class="layui-input" style="height: 100px;padding-left: 10px;" placeholder="text......">${vo.userText}</textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">Type：</label>
        <div class="layui-input-block">
            <input name="userType" type="radio" value="admin" ${vo.userType=='admin'?'checked':''} class="layui-input" title="admin" disabled/>
            <input name="userType" type="radio" value="user" ${vo.userType=='user'?'checked':''} class="layui-input" title="user" disabled/>
        </div>
    </div>
</form>
<script src="js/layui/layui.js" charset="utf-8"></script>
<script>
    layui.use(['form', 'layedit', 'laydate'], function () {
        let form = layui.form, layer = layui.layer, layedit = layui.layedit;
        //创建一个编辑器
        let editIndex = layedit.build('LAY_demo_editor');
    });
</script>
</body>
</html>
