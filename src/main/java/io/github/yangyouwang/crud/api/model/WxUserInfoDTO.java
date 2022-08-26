package io.github.yangyouwang.crud.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * Description: 加密微信用户信息DTO<br/>
 * date: 2022/8/26 23:19<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Data
@ApiModel("加密微信用户信息DTO")
public class WxUserInfoDTO implements Serializable {

    @ApiModelProperty("敏感数据")
    private String encryptedData;

    @ApiModelProperty("密钥")
    private String sessionKey;

    @ApiModelProperty("偏移向量")
    private String iv;
}
