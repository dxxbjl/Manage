package io.github.yangyouwang.crud.api.controller;

import io.github.yangyouwang.common.annotation.*;
import io.github.yangyouwang.common.constant.ApiVersionConsts;
import io.github.yangyouwang.common.constant.CacheConsts;
import io.github.yangyouwang.common.domain.ApiContext;
import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.common.enums.BusinessType;
import io.github.yangyouwang.core.util.JwtTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
@Api(tags = "demo控制层")
public class ApiDemoController {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @ApiVersion(value = ApiVersionConsts.API_V1,group = ApiVersionConsts.SWAGGER_API_V1)
    @ApiOperation(value="获取用户token")
    @GetMapping("/getUserToken")
    @PassToken
    public Map<String,Object> userToken() {
        Map<String,Object> result = new HashMap<>(16);
        String token = JwtTokenUtil.buildJWT("123");
        result.put("token",token);
        return result;
    }

    @ApiVersion(value = ApiVersionConsts.API_V1,group = ApiVersionConsts.SWAGGER_API_V1)
    @ApiOperation(value="获取用户信息")
    @GetMapping("/getUserInfo")
    public Map<String,Object> userInfo() {
        Long userId = ApiContext.getUserId();
        Map<String,Object> result = new HashMap<>(16);
        result.put("userId",userId);
        return result;
    }

    @ApiVersion(value = ApiVersionConsts.API_V1,group = ApiVersionConsts.SWAGGER_API_V1)
    @ApiOperation(value="生成幂等token")
    @GetMapping("/getIdempotentToken")
    @PassToken
    public Result idempotentToken() {
        String uuid = UUID.randomUUID().toString();
        String token = CacheConsts.REDIS_TOKEN_PREFIX  + uuid;
        redisTemplate.opsForValue().set(token, uuid, 10, TimeUnit.SECONDS);
        return Result.success("幂等token",uuid);
    }

    @ApiVersion(value = ApiVersionConsts.API_V1,group = ApiVersionConsts.SWAGGER_API_V1)
    @ApiOperation(value="测试幂等")
    @GetMapping("/checkIdempotent")
    @PassToken
    @ApiIdempotent
    public String checkIdempotent() {
        return "OK";
    }

    @ApiVersion(value = ApiVersionConsts.API_V1,group = ApiVersionConsts.SWAGGER_API_V1)
    @ApiOperation(value="抛异常记录日志")
    @GetMapping("/errorLog")
    @PassToken
    public void errorLog() {
        // 会出现错误
        int result = 10 / 0;
    }

    @ApiVersion(value = ApiVersionConsts.API_V1,group = ApiVersionConsts.SWAGGER_API_V1)
    @ApiOperation(value="记录操作日志")
    @GetMapping("/log")
    @CrudLog(title = "记录操作日志",businessType = BusinessType.INSERT)
    @PassToken
    public String log() {
        return "OK";
    }
}
