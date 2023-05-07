<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>addUser</title>
    <script src="js/jquery.js"></script>
    <link rel="stylesheet" href="js/layui/css/layui.css">
</head>
<body style="padding: 20px">
<form class="layui-form" action="">

    <div class="layui-form-item">
        <label class="layui-form-label">UserName：</label>
        <div class="layui-input-block">
            <input type="text" name="username" lay-verify="required" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">Password：</label>
        <div class="layui-input-block">
            <input type="text" name="password" lay-verify="required" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">Name：</label>
        <div class="layui-input-block">
            <input type="text" name="realName" lay-verify="required" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">Gender：</label>
        <div class="layui-input-block">
            <input name="userSex" type="radio" value="male" checked="checked" class="layui-input" title="male"/>
            <input name="userSex" type="radio" value="female" class="layui-input" title="female"/>
            <input name="userSex" type="radio" value="other" class="layui-input" title="other"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">Phone：</label>
        <div class="layui-input-block">
            <input type="number" name="userPhone" lay-verify="required" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">Text：</label>
        <div class="layui-input-block">
            <textarea type="text" name="userText" class="layui-input" style="height: 100px;padding-left: 10px;" placeholder="text......"></textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">Type：</label>
        <div class="layui-input-block">
            <input name="userType" type="radio" value="admin" checked="checked" class="layui-input" title="admin"/>
            <input name="userType" type="radio" value="user" class="layui-input" title="user"/>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button type="submit" class="layui-btn" lay-submit="" lay-filter="submit-button">add</button>
            <button type="reset" class="layui-btn layui-btn-primary">reset</button>
        </div>
    </div>
</form>
<script src="js/layui/layui.js" charset="utf-8"></script>
<script>
    layui.use(['form', 'layedit', 'laydate'], function () {
        let form = layui.form, layer = layui.layer, layedit = layui.layedit;
        //创建一个编辑器
        let editIndex = layedit.build('LAY_demo_editor');
        //自定义验证规则
        form.verify({
            content: function (value) {
                layedit.sync(editIndex);
            }
        });

        //监听提交
        form.on('submit(submit-button)', function (data) {
            $.post("UserServlet?action=add&", data.field, function () {
                let index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                parent.layer.close(index); //再执行关闭
                parent.location.reload();//刷新父级页面

            }, "text");//这里用的是post提交，如果不懂可以参考JQuery中ajax提
            return false;
        });
    });
</script>
</body>
</html>
