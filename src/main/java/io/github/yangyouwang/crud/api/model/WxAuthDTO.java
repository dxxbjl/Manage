package io.github.yangyouwang.crud.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Description: 微信授权对象DTO <br/>
 * date: 2022/8/3 19:21<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Data
@ApiModel("微信授权DTO")
public class WxAuthDTO implements Serializable {
    /**
     * code
     */
    @ApiModelProperty("code")
    private String code;
}
