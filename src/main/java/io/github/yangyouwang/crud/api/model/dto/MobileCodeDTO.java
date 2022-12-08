package io.github.yangyouwang.crud.api.model.dto;

import io.github.yangyouwang.common.annotation.validation.Phone;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Description: 手机发送验证码DTO<br/>
 * date: 2022/12/5 13:14<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Data
@ApiModel("手机发送验证码DTO")
public class MobileCodeDTO {

    @Phone
    @NotBlank
    @ApiModelProperty(value = "手机号")
    private String mobile;
}
