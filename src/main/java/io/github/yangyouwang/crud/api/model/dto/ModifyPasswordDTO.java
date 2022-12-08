package io.github.yangyouwang.crud.api.model.dto;

import io.github.yangyouwang.common.annotation.validation.Phone;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Description: 修改密码DTO<br/>
 * date: 2022/12/5 22:27<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Data
@ApiModel("修改密码DTO")
public class ModifyPasswordDTO {

    @Phone
    @NotBlank
    @ApiModelProperty(value = "手机号")
    private String mobile;

    @NotBlank
    @ApiModelProperty(value = "验证码")
    private String code;

    @NotBlank
    @ApiModelProperty(value = "旧密码")
    private String passwordOne;

    @NotBlank
    @ApiModelProperty(value = "新密码")
    private String passwordTwo;
}
