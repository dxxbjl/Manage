# crud

## 项目介绍
    1.需求定义：外包项目如雨后春笋，开发工期被迫压缩，为了开发人员专注项目业务，下班能陪老婆、孩子。
    2.产品定位: 简约后台管理系统
    3.项目特点：此项目代码清晰、界面简洁、springboot + layuiadmin 构建的单体后台管理系统。

## 软件架构
- 核心框架：Spring Boot
- 权限框架：SpringSecurity
- 模板引擎：Thymeleaf
- 持久层框架：Mybatis-Plus
- 日志管理：LogBack
- 工具类：Apache Commons、Hutool
- 视图框架：Spring MVC
- 工作流：Activiti6
- 定时器：Quartz
- 数据库连接池：Druid
- 日志管理：logback
- 页面交互：layuiAdmin
- 验证框架：hibernate-Validation
- 接口文档：Swagger

## 环境需求
    JDK >= 1.8
    MySQL >= 5.7
    Maven >= 3.0

## 使用说明
    
    1.控制层接口版本管理、包装响应Result返回值进行全局处理，使用@ResponseResultBody注解
    
    2.定义接口版本，在方法中配置@ApiVersion注解
    
    3.跳过jwt安全认证只需要加入@PassToken注解
    
    4.使用@CrudLog注解可以将系统错误日志记录到数据库
    
    5.security菜单、按钮权限
```
@PreAuthorize("hasAuthority('权限标识')") // java代码
sec:authorize="hasAuthority('权限标识')" // thymeleaf声明
```
    
    6.获取header传入的token中userId
```
 Long userId = ApiContext.getUserId();
```
    7.list转化tree结构
```
 ListToTree treeBuilder = new ListToTreeImpl();
 treeBuilder.toTree(menus);
```
    8.发送验证码
```
 SampleEmail.sample(邮件地址,标题,内容);
```
    9.上传文件到oss
```
 SampleOSS.upload(文件流, 自定义上传路径);
```
    10.发送短信
```
 SampleSms.sendSms(手机号,模版号,签名); 
```   
    11.字典渲染layui
```
   layui.config({
        base: '/static/layuiadmin/' //静态资源所在路径
    }).extend({
      index: 'lib/index' //主入口模块
   }).use(["crud"],function(){
    let crud = layui.crud; 
     // 获取枚举
     crud.getDictValue('字典类型',枚举值);
     // radio赋值
     crud.setRadio("id节点","字典类型","默认值");
     // select赋值
     crud.setSelect("id节点","字典类型",'默认值');
  })
```   
    
## 项目运行截图
    
    登陆功能
   ![运行截图](https://gitee.com/yangyouwang/crud/raw/master/img/0.png "0.png")
    
    首页
   ![运行截图](https://gitee.com/yangyouwang/crud/raw/master/img/1.png "1.png")
   
    用户管理模块
   ![运行截图](https://gitee.com/yangyouwang/crud/raw/master/img/2.png "2.png")
   
    角色管理模块
   ![运行截图](https://gitee.com/yangyouwang/crud/raw/master/img/3.png "3.png")
   
    菜单管理模块
   ![运行截图](https://gitee.com/yangyouwang/crud/raw/master/img/4.png "4.png")
   
    swagger接口文档
   ![运行截图](https://gitee.com/yangyouwang/crud/raw/master/img/5.png "5.png")
   
    druid监控
   ![运行截图](https://gitee.com/yangyouwang/crud/raw/master/img/6.png "6.png")
   
    定时任务
   ![运行截图](https://gitee.com/yangyouwang/crud/raw/master/img/8.png "8.png")
   
    工作流
   ![运行截图](https://gitee.com/yangyouwang/crud/raw/master/img/9.png "9.png")
   ![运行截图](https://gitee.com/yangyouwang/crud/raw/master/img/10.png "10.png")
   
    日志管理
   ![运行截图](https://gitee.com/yangyouwang/crud/raw/master/img/11.png "11.png")
   
    字典管理
   ![运行截图](https://gitee.com/yangyouwang/crud/raw/master/img/12.png "12.png")
   ![运行截图](https://gitee.com/yangyouwang/crud/raw/master/img/13.png "13.png")