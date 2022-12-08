package io.github.yangyouwang.crud.api.model.dto;

import io.github.yangyouwang.common.annotation.validation.Phone;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * Description: 用户注册DTO <br/>
 * date: 2022/12/6 1:09<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Data
@ApiModel("用户注册DTO")
public class RegisterDTO {

    @Phone
    @NotBlank
    @ApiModelProperty(value = "手机号")
    private String mobile;

    @NotBlank
    @ApiModelProperty(value = "验证码")
    private String code;

    @NotBlank
    @ApiModelProperty(value = "旧密码")
    private String password;
}
