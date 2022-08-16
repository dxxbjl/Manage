package io.github.yangyouwang.crud.api.controller;

import io.github.yangyouwang.common.annotation.ApiVersion;
import io.github.yangyouwang.common.annotation.PassToken;
import io.github.yangyouwang.common.annotation.ResponseResultBody;
import io.github.yangyouwang.common.constant.ApiVersionConstant;
import io.github.yangyouwang.core.context.ApiContext;
import io.github.yangyouwang.crud.api.model.*;
import io.github.yangyouwang.crud.api.service.ApiUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
@Api(tags = "用户相关接口")
@RequiredArgsConstructor
@ResponseResultBody
public class ApiUserController {

    private final ApiUserService apiUserService;
    /**
     * 微信授权
     * @return 响应
     */
    @ApiVersion(value = ApiVersionConstant.API_V1,group = ApiVersionConstant.SWAGGER_API_V1)
    @PostMapping("/wx_auth")
    @ApiOperation(value="微信授权", notes="微信授权")
    @PassToken
    public WxAuthVO wxAuth(@Valid @RequestBody WxAuthDTO wxAuthDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return apiUserService.wxAuth(wxAuthDTO);
    }
    /**
     * 用户名密码授权
     * @return 响应
     */
    @ApiVersion(value = ApiVersionConstant.API_V1,group = ApiVersionConstant.SWAGGER_API_V1)
    @PostMapping("/password_auth")
    @ApiOperation(value="用户名密码授权", notes="用户名密码授权")
    @PassToken
    public UserAuthVO passwordAuth(@Valid @RequestBody PasswordAuthDTO passwordAuthDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return apiUserService.passwordAuth(passwordAuthDTO);
    }
    /**
     * 用户详情
     * @return 响应
     */
    @ApiVersion(value = ApiVersionConstant.API_V1,group = ApiVersionConstant.SWAGGER_API_V1)
    @GetMapping("/user_info")
    @ApiOperation(value="用户详情", notes="用户详情")
    public UserInfoVO userInfo() {
        Long userId = ApiContext.getUserId();
        return apiUserService.getUserInfoById(userId);
    }
}
