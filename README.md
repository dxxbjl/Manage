## CRUD企业级脚手架
    当前版本：简约后台管理系统v1.0
    
    基于框架：SpringBoot SpringSecurity JPA Thymeleaf Swagger Validation layuiAdmin

## 操作说明

   ![运行截图](https://raw.githubusercontent.com/YangYouWang/crud/master/img/7.png "7.png")
    
    控制层接口版本管理、包装响应Result返回值进行全局处理，使用@ResponseResultBody注解
    
    定义接口版本，在方法中配置@ApiVersion注解
    
    跳过jwt安全认证只需要加入@PassToken
    
    
```
Long userId = ApiContext.getUserId(); //  获取header传入的token中userId
```
   
```
 ListToTree treeBuilder = new ListToTreeImpl();
 treeBuilder.toTree(menus); // list转化tree结构
```

```
  SampleEmail.sample(邮件地址,标题,内容); // 发送验证码
```

```
  SampleOSS.upload(文件流, 自定义上传路径);// 上传文件到oss
```

```
  SampleSms.sendSms(手机号,模版号,签名); // 发送短信
```   

    security菜单权限、按钮级别权限配置不多说了都是原生的，没有过度封装。
    
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