## 简约后台管理系统
    当前版本：v1.0
    
## 介绍
    特点：轻量、简化、避免开发人员重复造轮子。
    
## 基于框架

    SpringBoot SpringSecurity JPA Thymeleaf Swagger Validation layuiAdmin

## 操作说明

   ![运行截图](https://raw.githubusercontent.com/YangYouWang/crud/master/img/7.png "7.png")
    
    1.控制层接口版本管理、包装响应Result返回值进行全局处理，使用@ResponseResultBody注解
    
    2.定义接口版本，在方法中配置@ApiVersion注解
    
    3.跳过jwt安全认证只需要加入@PassToken注解
    
    4.方法中加入@CrudLog注解可拦截异常信息并记录到数据库
    
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
    
## 项目运行截图
    
    登陆功能
   ![运行截图](https://raw.githubusercontent.com/YangYouWang/crud/master/img/0.png "0.png")
    
    首页
   ![运行截图](https://raw.githubusercontent.com/YangYouWang/crud/master/img/1.png "1.png")
   
    用户管理模块
   ![运行截图](https://raw.githubusercontent.com/YangYouWang/crud/master/img/2.png "2.png")
   
    角色管理模块
   ![运行截图](https://raw.githubusercontent.com/YangYouWang/crud/master/img/3.png "3.png")
   
    菜单管理模块
   ![运行截图](https://raw.githubusercontent.com/YangYouWang/crud/master/img/4.png "4.png")
   
    swagger接口文档
   ![运行截图](https://raw.githubusercontent.com/YangYouWang/crud/master/img/5.png "5.png")
   
    druid监控
   ![运行截图](https://raw.githubusercontent.com/YangYouWang/crud/master/img/6.png "6.png")
 **革命尚未成功，同志仍须努力**