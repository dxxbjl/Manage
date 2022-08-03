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
 * @since 2022-08-03
 */
@Data
@TableName("app_oauth")
@ApiModel(value="Oauth对象", description="授权表")
public class Oauth {

    private static final long serialVersionUID = 1L;

    /** 主键id */
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "open_id")
    private String openId;

    @ApiModelProperty(value = "类型 1 微信")
    private Integer appType;

    @ApiModelProperty(value = "用户id")
    private Long userId;


}
