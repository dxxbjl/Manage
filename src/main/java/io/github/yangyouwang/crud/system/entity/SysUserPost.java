package io.github.yangyouwang.crud.system.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 用户岗位关联表
 * </p>
 *
 * @author yangyouwang
 * @since 2022-09-15
 */
@Data
@ApiModel(value="SysUserPost对象", description="用户岗位关联表")
public class SysUserPost implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户外键")
    private Long userId;

    @ApiModelProperty(value = "岗位外键")
    private Long postId;


}
