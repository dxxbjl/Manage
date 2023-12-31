package io.github.yangyouwang.module.api.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description: 用户授权对象VO<br/>
 * date: 2022/8/3 19:47<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Data
@ApiModel("授权VO")
public class UserAuthVO {
    /**
     * 应用token
     */
    @ApiModelProperty("应用token")
    private String token;
}
