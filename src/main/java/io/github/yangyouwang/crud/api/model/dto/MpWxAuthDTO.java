package io.github.yangyouwang.crud.api.model.dto;

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
@ApiModel("微信小程序授权DTO")
public class MpWxAuthDTO implements Serializable {

    @ApiModelProperty("code")
    private String code;

    @ApiModelProperty("头像")
    private String avatarUrl;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("性别")
    private Integer gender;
}
