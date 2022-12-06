package io.github.yangyouwang.crud.api.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description: 用户绑定应用<br/>
 * date: 2022/12/7 0:04<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Data
@ApiModel("用户绑定应用")
public class UserApplyVO {

    @ApiModelProperty(value = "类型：MP_WX 微信小程序、WX_APP 微信、PHONE APP登录、QQ QQ登录、PASSWORD 密码")
    private String appType;

    @ApiModelProperty(value = "状态：Y、绑定")
    private String state;
}
