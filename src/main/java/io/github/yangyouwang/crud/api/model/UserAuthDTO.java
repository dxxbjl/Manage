package io.github.yangyouwang.crud.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Description: 用户授权对象DTO <br/>
 * date: 2022/8/3 19:21<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("授权DTO")
public class UserAuthDTO implements Serializable {
    /**
     * code
     */
    @ApiModelProperty("code")
    private String code;
    /**
     * 类型 1 微信
     */
    @ApiModelProperty("类型 1 微信")
    private Integer appType;
}
