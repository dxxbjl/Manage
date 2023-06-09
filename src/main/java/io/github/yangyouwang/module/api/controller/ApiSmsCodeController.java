package io.github.yangyouwang.module.api.controller;

import io.github.yangyouwang.common.annotation.ApiVersion;
import io.github.yangyouwang.common.annotation.PassToken;
import io.github.yangyouwang.common.annotation.ResponseResultBody;
import io.github.yangyouwang.common.constant.ApiVersionConsts;
import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.module.api.model.dto.MobileCodeDTO;
import io.github.yangyouwang.module.api.service.ApiSmsCodeService;
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
 * Description: 手机验证码接口 <br/>
 * date: 2022/12/6 11:18<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/api/{version}/sms_code")
@Api(tags = "手机验证码接口")
@RequiredArgsConstructor
@ResponseResultBody
public class ApiSmsCodeController {

    private final ApiSmsCodeService apiSmsCodeService;

    /**
     * 发送手机验证码
     */
    @ApiVersion(value = ApiVersionConsts.API_V1,group = ApiVersionConsts.SWAGGER_API_V1)
    @PostMapping("/mobile_code")
    @ApiOperation(value="发送手机验证码", notes="发送手机验证码")
    @PassToken
    public Result mobileCode(@Valid @RequestBody MobileCodeDTO mobileCodeDTO,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        String mobile = mobileCodeDTO.getMobile();
        boolean flag = apiSmsCodeService.sendMobileCode(mobile);
        return Result.success(flag);
    }
}
