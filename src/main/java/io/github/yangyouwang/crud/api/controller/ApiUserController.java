package io.github.yangyouwang.crud.api.controller;

import io.github.yangyouwang.common.annotation.ApiVersion;
import io.github.yangyouwang.common.annotation.PassToken;
import io.github.yangyouwang.common.annotation.ResponseResultBody;
import io.github.yangyouwang.common.constant.ApiVersionConstant;
import io.github.yangyouwang.crud.api.model.UserAuthDTO;
import io.github.yangyouwang.crud.api.model.UserAuthVO;
import io.github.yangyouwang.crud.api.service.ApiUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

/**
 * Description: 用户相关接口 <br/>
 * date: 2022/8/3 19:04<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/api/{version}/user")
@Api(tags = "ApiUserController", description = "用户相关接口")
@RequiredArgsConstructor
@ResponseResultBody
public class ApiUserController {

    private final ApiUserService apiUserService;

    /**
     * 用户授权
     * @return 响应
     */
    @ApiVersion(value = ApiVersionConstant.API_V1,group = ApiVersionConstant.SWAGGER_API_V1)
    @PostMapping("/user_auth")
    @ApiOperation(value="用户授权", notes="用户授权")
    @PassToken
    public UserAuthVO userAuth(@Valid @RequestBody UserAuthDTO userAuthDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return apiUserService.userAuth(userAuthDTO);
    }
}
