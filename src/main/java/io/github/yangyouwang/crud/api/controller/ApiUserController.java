package io.github.yangyouwang.crud.api.controller;

import io.github.yangyouwang.common.annotation.ApiVersion;
import io.github.yangyouwang.common.annotation.PassToken;
import io.github.yangyouwang.common.annotation.ResponseResultBody;
import io.github.yangyouwang.common.constant.ApiVersionConstant;
import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.crud.api.model.dto.*;
import io.github.yangyouwang.crud.api.model.vo.UserAuthVO;
import io.github.yangyouwang.crud.api.model.vo.UserInfoVO;
import io.github.yangyouwang.crud.api.model.vo.MpWxAuthVO;
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
     * 微信小程序授权
     * @return 响应
     */
    @ApiVersion(value = ApiVersionConstant.API_V1,group = ApiVersionConstant.SWAGGER_API_V1)
    @PostMapping("/mp/wx/auth")
    @ApiOperation(value="微信小程序授权", notes="微信小程序授权")
    @PassToken
    public MpWxAuthVO mpWxAuth(@Valid @RequestBody MpWxAuthDTO mpWxAuthDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return apiUserService.mpWxAuth(mpWxAuthDTO);
    }
    /**
     * 用户名密码授权
     * @return 响应
     */
    @ApiVersion(value = ApiVersionConstant.API_V1,group = ApiVersionConstant.SWAGGER_API_V1)
    @PostMapping("/password/auth")
    @ApiOperation(value="用户名密码授权", notes="用户名密码授权")
    @PassToken
    public UserAuthVO passwordAuth(@Valid @RequestBody PasswordAuthDTO passwordAuthDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return apiUserService.passwordAuth(passwordAuthDTO);
    }
    /**
     * 手机号验证码授权
     * @return 响应
     */
    @ApiVersion(value = ApiVersionConstant.API_V1,group = ApiVersionConstant.SWAGGER_API_V1)
    @PostMapping("/mobile/auth")
    @ApiOperation(value="手机号验证码授权", notes="手机号验证码授权")
    @PassToken
    public UserAuthVO mobileAuth(@Valid @RequestBody MobileAuthDTO mobileAuthDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return apiUserService.mobileAuth(mobileAuthDTO);
    }

    /**
     * 获取QQ授权code
     * @return 响应
     */
    @ApiVersion(value = ApiVersionConstant.API_V1,group = ApiVersionConstant.SWAGGER_API_V1)
    @GetMapping("/qq/code")
    @ApiOperation(value="获取QQ授权code", notes="获取QQ授权code")
    @PassToken
    public Result qqCode() {
        String code = apiUserService.getQQCode();
        return Result.success("qq登录code",code);
    }
    /**
     * QQ授权回调
     * @return 响应
     */
    @ApiVersion(value = ApiVersionConstant.API_V1,group = ApiVersionConstant.SWAGGER_API_V1)
    @PostMapping("/qq/auth/callback")
    @ApiOperation(value="QQ授权回调", notes="QQ授权回调")
    @PassToken
    public UserAuthVO qqAuthCallback(@Valid @RequestBody QQAuthDTO qqAuthDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return apiUserService.qqAuthCallback(qqAuthDTO);
    }
    /**
     * 微信APP授权
     * @return 响应
     */
    @ApiVersion(value = ApiVersionConstant.API_V1,group = ApiVersionConstant.SWAGGER_API_V1)
    @PostMapping("/wx/auth")
    @ApiOperation(value="微信APP授权", notes="微信APP授权")
    @PassToken
    public UserAuthVO wxAuth(@Valid @RequestBody WxAuthDTO wxAuthDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return apiUserService.wxAuth(wxAuthDTO);
    }
    /**
     * 用户详情
     * @return 响应
     */
    @ApiVersion(value = ApiVersionConstant.API_V1,group = ApiVersionConstant.SWAGGER_API_V1)
    @GetMapping("/info")
    @ApiOperation(value="用户详情", notes="用户详情")
    public UserInfoVO userInfo() {
        return apiUserService.getUserInfo();
    }
    /**
     * 解密微信用户信息
     * @param wxUserInfoDTO 加密微信用户信息
     * @return 响应
     */
    @ApiVersion(value = ApiVersionConstant.API_V1,group = ApiVersionConstant.SWAGGER_API_V1)
    @PostMapping("/decode/wx")
    @ApiOperation(value="解密微信用户信息", notes="解密微信用户信息")
    public Result decodeWxUser(@Valid @RequestBody WxUserInfoDTO wxUserInfoDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        String userPhoneNumber = apiUserService.decodeWxUser(wxUserInfoDTO);
        String message = String.format("绑定手机号为：%s", userPhoneNumber);
        return Result.success(message,userPhoneNumber);
    }
    /**
     * 更新用户信息
     * @param userInfoDTO 用户信息
     * @return 响应
     */
    @ApiVersion(value = ApiVersionConstant.API_V1,group = ApiVersionConstant.SWAGGER_API_V1)
    @PostMapping("/modify")
    @ApiOperation(value="更新用户信息", notes="更新用户信息")
    public Result modifyUser(@Valid @RequestBody UserInfoDTO userInfoDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        boolean flag = apiUserService.modifyUser(userInfoDTO);
        return Result.success(flag);
    }

    /**
     * 修改密码
     * @param modifyPasswordDTO 修改密码DTO
     * @return 响应
     */
    @ApiVersion(value = ApiVersionConstant.API_V1,group = ApiVersionConstant.SWAGGER_API_V1)
    @PostMapping("/modifyPassword")
    @ApiOperation(value="修改密码", notes="修改密码")
    public Result modifyPassword(@Valid @RequestBody ModifyPasswordDTO modifyPasswordDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        boolean flag = apiUserService.modifyPassword(modifyPasswordDTO);
        return Result.success(flag);
    }

    /**
     * 用户注册
     * @param registerUserDTO 用户注册DTO
     * @return 响应
     */
    @ApiVersion(value = ApiVersionConstant.API_V1,group = ApiVersionConstant.SWAGGER_API_V1)
    @PostMapping("/register")
    @ApiOperation(value="用户注册", notes="用户注册")
    @PassToken
    public Result registerUser(@Valid @RequestBody RegisterUserDTO registerUserDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        boolean flag = apiUserService.registerUser(registerUserDTO);
        return Result.success(flag);
    }
}
