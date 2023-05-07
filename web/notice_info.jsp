<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>noticeInfo</title>
    <script src="js/jquery.js"></script>
    <link rel="stylesheet" href="js/layui/css/layui.css">
</head>
<body style="padding: 20px">
<form class="layui-form" action="">
    <div class="layui-form-item">
        <label class="layui-form-label">Name：</label>
        <div class="layui-input-block" style="padding: 9px 15px;">
            ${vo.noticeName}
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">Text：</label>
        <div class="layui-input-block">
            <textarea type="text" name="noticeText" disabled="disabled" class="layui-input" style="height: 100px;padding-left: 10px;" placeholder="text......">${vo.noticeText}</textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">ShelfLife：</label>
        <div class="layui-input-block" style="padding: 9px 15px;">
            ${vo.noticeType}
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">CreateTime：</label>
        <div class="layui-input-block" style="padding: 9px 15px;">
            ${vo.createDate}
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
