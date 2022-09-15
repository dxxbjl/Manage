package io.github.yangyouwang.crud.system.entity;

import io.github.yangyouwang.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 岗位表
 * </p>
 *
 * @author yangyouwang
 * @since 2022-09-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="SysPost对象", description="岗位表")
public class SysPost extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "岗位编码")
    private String postCode;

    @ApiModelProperty(value = "岗位名称")
    private String postName;

    @ApiModelProperty(value = "显示顺序")
    private Integer orderNum;

    @ApiModelProperty(value = "是否启用 Y 启用 N 禁用")
    private String enabled;


}
