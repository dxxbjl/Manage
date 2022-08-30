<!DOCTYPE html>
<html lang="zh" xmlns:th="http://www.thymeleaf.org">
<head>
  <title>编辑${table.comment!}</title>
  <div th:replace="common/link::header"></div>
</head>
<body>
<div class="layui-fluid">
  <div class="layui-row">
    <div class="layui-form" lay-filter="${table.entityPath}Form" id="${table.entityPath}Form">
      <input type="hidden" name="id" id="id">
      <#list table.fields as field>
        <div class="layui-form-item">
          <label class="layui-form-label">${field.comment}</label>
          <div class="layui-input-inline">
            <input type="text" class="layui-input" name="${field.propertyName}" placeholder="请输入${field.comment}"/>
          </div>
        </div>
      </#list>
      <div class="layui-form-item layui-hide">
        <input type="button" lay-submit lay-filter="save-submit" id="save-submit" value="确认">
      </div>
    </div>
  </div>
</div>
<div th:replace="common/script::footer"></div>
<script th:inline="javascript">
  layui.config({
      base: '/static/layuiadmin/' //静态资源所在路径
  }).extend({
     index: 'lib/index', //主入口模块
  }).use(['index', 'form','crud'], function(){
    let $ = layui.$,
            form = layui.form,
            crud = layui.crud,
            ${table.entityPath} = [[${r'${'}${table.entityPath}${r'}'}]];
    form.val('${table.entityPath}Form',${table.entityPath});
    // 编辑
    form.on('submit(save-submit)', function(data) {
      $.ajax({
        type: 'POST',
        url:  ctx + '<#if package.ModuleName??>/${package.ModuleName}</#if>/${table.entityPath}/modify',
        data: JSON.stringify(data.field),
        contentType:'application/json;charset=UTF-8',
        dataType: 'json',
        success: function(result) {
          layer.msg(result.message);
          if (result.code === 200) {
            let index = parent.layer.getFrameIndex(window.name);
            // 关闭
            parent.layer.close(index);
            // 刷新
            parent.location.reload();
          }
        }
      });
    });
  });
</script>
</body>
</html>