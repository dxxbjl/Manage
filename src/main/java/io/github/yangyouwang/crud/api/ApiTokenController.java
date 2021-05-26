package io.github.yangyouwang.crud.api;

import io.github.yangyouwang.common.annotation.PassToken;
import io.github.yangyouwang.common.constant.JwtConstants;
import io.github.yangyouwang.common.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/api/token")
@Api(tags = "ApiTokenController", description = "幂等接口控制层")
public class ApiTokenController {

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation(value="生成幂等token接口")
    @GetMapping("/createToken")
    @PassToken
    public Result createToken() {
        String uuid = UUID.randomUUID().toString();
        String token = JwtConstants.REDIS_TOKEN_PREFIX + uuid;
        redisTemplate.opsForValue().set(token, token, 10,TimeUnit.SECONDS);
        return Result.success(token);
    }
}
