package io.github.yangyouwang.module.api.controller;

import io.github.yangyouwang.common.annotation.ApiVersion;
import io.github.yangyouwang.common.annotation.PassToken;
import io.github.yangyouwang.common.annotation.ResponseResultBody;
import io.github.yangyouwang.common.constant.ApiVersionConsts;
import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.module.api.model.dto.*;
import io.github.yangyouwang.module.api.model.vo.UserApplyVO;
import io.github.yangyouwang.module.api.model.vo.UserAuthVO;
import io.github.yangyouwang.module.api.model.vo.UserInfoVO;
import io.github.yangyouwang.module.api.model.vo.MpWxAuthVO;
import io.github.yangyouwang.module.api.service.ApiUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
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
    @ApiVersion(value = ApiVersionConsts.API_V1,group = ApiVersionConsts.SWAGGER_API_V1)
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
    @ApiVersion(value = ApiVersionConsts.API_V1,group = ApiVersionConsts.SWAGGER_API_V1)
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
    @ApiVersion(value = ApiVersionConsts.API_V1,group = ApiVersionConsts.SWAGGER_API_V1)
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
    @ApiVersion(value = ApiVersionConsts.API_V1,group = ApiVersionConsts.SWAGGER_API_V1)
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
    @ApiVersion(value = ApiVersionConsts.API_V1,group = ApiVersionConsts.SWAGGER_API_V1)
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
    @ApiVersion(value = ApiVersionConsts.API_V1,group = ApiVersionConsts.SWAGGER_API_V1)
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
    @ApiVersion(value = ApiVersionConsts.API_V1,group = ApiVersionConsts.SWAGGER_API_V1)
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
    @ApiVersion(value = ApiVersionConsts.API_V1,group = ApiVersionConsts.SWAGGER_API_V1)
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
    @ApiVersion(value = ApiVersionConsts.API_V1,group = ApiVersionConsts.SWAGGER_API_V1)
    @PostMapping("/modify")
    @ApiOperation(value="更新用户信息", notes="更新用户信息")
    public Result modifyUser(@Valid @RequestBody UserInfoDTO userInfoDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        apiUserService.modifyUser(userInfoDTO);
        return Result.success("修改成功");
    }

    /**
     * 修改密码
     * @param modifyPasswordDTO 修改密码DTO
     * @return 响应
     */
    @ApiVersion(value = ApiVersionConsts.API_V1,group = ApiVersionConsts.SWAGGER_API_V1)
    @PostMapping("/modifyPassword")
    @ApiOperation(value="修改密码", notes="修改密码")
    public Result modifyPassword(@Valid @RequestBody ModifyPasswordDTO modifyPasswordDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        apiUserService.modifyPassword(modifyPasswordDTO);
        return Result.success("修改成功");
    }

    /**
     * 用户注册
     * @param registerDTO 用户注册DTO
     * @return 响应
     */
    @ApiVersion(value = ApiVersionConsts.API_V1,group = ApiVersionConsts.SWAGGER_API_V1)
    @PostMapping("/register")
    @ApiOperation(value="用户注册", notes="用户注册")
    @PassToken
    public Result userRegister(@Valid @RequestBody RegisterDTO registerDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        apiUserService.userRegister(registerDTO);
        return Result.success("注册成功");
    }

    /**
     * 获取用户绑定应用列表
     * @return 响应
     */
    @ApiVersion(value = ApiVersionConsts.API_V1,group = ApiVersionConsts.SWAGGER_API_V1)
    @GetMapping("/user_apply")
    @ApiOperation(value="获取用户绑定应用列表", notes="获取用户绑定应用列表")
    public List<UserApplyVO> userApply() {
        return apiUserService.getUserApplyList();
    }
}
