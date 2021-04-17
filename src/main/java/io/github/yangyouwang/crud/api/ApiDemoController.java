package io.github.yangyouwang.crud.api;

import io.github.yangyouwang.common.annotation.*;
import io.github.yangyouwang.common.constant.ApiVersionConstant;
import io.github.yangyouwang.core.context.ApiContext;
import io.github.yangyouwang.core.util.JwtTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangyouwang
 * @title: ApiDemoController
 * @projectName crud
 * @description: demo
 * @date 2021/3/3010:24 PM
 */
@ResponseResultBody
@Controller
@RequestMapping("/api/{version}/demo")
@Api(tags = "ApiDemoController", description = "demo控制层")
public class ApiDemoController {


    @ApiOperation(value="测试hello接口")
    @ApiVersion(value = ApiVersionConstant.API_V1,group = ApiVersionConstant.SWAGGER_API_V1)
    @GetMapping("/hello")
    public Map<String,Object> hello() {
        Long userId = ApiContext.getUserId();
        Map<String,Object> result = new HashMap<>(16);
        result.put("userId",userId);
        return result;
    }


    @ApiOperation(value="获取token")
    @ApiVersion(value = ApiVersionConstant.API_V1,group = ApiVersionConstant.SWAGGER_API_V1)
    @GetMapping("/getToken")
    @PassToken
    @ApiIdempotent
    public Map<String,Object> getToken() {
        Map<String,Object> result = new HashMap<>(16);
        result.put("token",JwtTokenUtil.buildJWT("123"));
        return result;
    }

    @ApiOperation(value="抛异常记录日志接口")
    @ApiVersion(value = ApiVersionConstant.API_V1,group = ApiVersionConstant.SWAGGER_API_V1)
    @GetMapping("/log")
    @CrudLog
    @PassToken
    public void log() {
        // 会出现错误
        int result = 10 / 0;
    }

}
