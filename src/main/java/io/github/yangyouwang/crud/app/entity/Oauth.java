package io.github.yangyouwang.crud.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 授权表
 * </p>
 *
 * @author yangyouwang
 * @since 2022-08-16
 */
@Data
@TableName("app_oauth")
@ApiModel(value="Oauth对象", description="授权表")
public class Oauth {

    private static final long serialVersionUID = 1L;

    /** 主键id */
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "秘钥")
    private String appSecret;

    @ApiModelProperty(value = "类型：wx微信、phone手机、password密码")
    private String appType;

    @ApiModelProperty(value = "用户id")
    private Long userId;
}
