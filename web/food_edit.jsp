<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>updateFood</title>
    <script src="js/jquery.js"></script>
    <link rel="stylesheet" href="js/layui/css/layui.css">
</head>
<body style="padding: 20px">
<form class="layui-form" action="">
    <input type="hidden" name="id" class="layui-input" value="${vo.id}">
    
    <div class="layui-form-item">
        <label class="layui-form-label">Name：</label>
        <div class="layui-input-block">
            <input type="text" name="foodName" lay-verify="required|foodName" class="layui-input" value="${vo.foodName}">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">Origin：</label>
        <div class="layui-input-block">
            <input type="text" name="foodLan" lay-verify="required|foodLan" class="layui-input" value="${vo.foodLan}">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">Merchant：</label>
        <div class="layui-input-block">
            <input type="text" name="foodTing" lay-verify="required|foodTing" class="layui-input" value="${vo.foodTing}">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">Date：</label>
        <div class="layui-input-block">
            <input type="text" name="foodDate" lay-verify="required|foodDate" class="layui-input" value="${vo.foodDate}">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">ShelfLife：</label>
        <div class="layui-input-block">
            <input type="number" name="foodLong" lay-verify="required|foodLong" class="layui-input" value="${vo.foodLong}">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">Price：</label>
        <div class="layui-input-block">
            <input type="number" name="foodPrice" lay-verify="required|foodPrice" class="layui-input" value="${vo.foodPrice}">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">Text：</label>
        <div class="layui-input-block">
            <textarea type="text" name="foodText" class="layui-input" style="height: 100px;padding-left: 10px;" placeholder="please input......">${vo.foodText}</textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button type="submit" class="layui-btn" lay-submit="" lay-filter="submit-button">save</button>
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
            $.post("FoodServlet?action=edit&", data.field, function () {
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
