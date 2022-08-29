package io.github.yangyouwang.crud.system.entity;

import io.github.yangyouwang.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户登录日志记录表
 * </p>
 *
 * @author yangyouwang
 * @since 2022-08-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="SysLoginLog对象", description="用户登录日志记录表")
public class SysLoginLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "登录IP")
    private String loginIp;

    @ApiModelProperty(value = "登录状态 0 失败 1 成功")
    private Long loginStatus;


}
