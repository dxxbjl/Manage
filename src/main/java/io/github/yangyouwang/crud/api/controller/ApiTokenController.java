package io.github.yangyouwang.crud.api.controller;

import io.github.yangyouwang.common.annotation.ApiVersion;
import io.github.yangyouwang.common.constant.ApiVersionConstant;
import io.github.yangyouwang.common.constant.CacheConsts;
import io.github.yangyouwang.common.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author yangyouwang
 * @title: ApiTokenController
 * @projectName crud
 * @description: api 幂等接口控制层
 * @date 2021/4/410:43 PM
 */
@RestController
@RequestMapping("/api/{version}/token")
@Api(tags = "ApiTokenController", description = "幂等接口控制层")
public class ApiTokenController {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @ApiVersion(value = ApiVersionConstant.API_V1,group = ApiVersionConstant.SWAGGER_API_V1)
    @ApiOperation(value="生成幂等token接口")
    @GetMapping("/createToken")
    public Result createToken() {
        String uuid = UUID.randomUUID().toString();
        String token = CacheConsts.REDIS_TOKEN_PREFIX  + uuid;
        redisTemplate.opsForValue().set(token, uuid, 10, TimeUnit.SECONDS);
        return Result.success(uuid);
    }
}
