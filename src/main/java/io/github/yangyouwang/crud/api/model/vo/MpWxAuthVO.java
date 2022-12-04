package io.github.yangyouwang.crud.api.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * Description: 微信小程序授权VO<br/>
 * date: 2022/8/3 19:47<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Data
@ApiModel("微信小程序授权VO")
public class MpWxAuthVO extends UserAuthVO implements Serializable {
    /**
     * 微信小程序sessionKey
     */
    @ApiModelProperty("微信小程序sessionKey")
    private String sessionKey;
}
