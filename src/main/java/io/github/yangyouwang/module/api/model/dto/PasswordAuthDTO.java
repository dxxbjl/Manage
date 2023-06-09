package io.github.yangyouwang.module.api.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description: 密码授权对象DTO <br/>
 * date: 2022/8/3 19:21<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Data
@ApiModel("密码授权DTO")
public class PasswordAuthDTO {

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "秘钥")
    private String appSecret;
}
