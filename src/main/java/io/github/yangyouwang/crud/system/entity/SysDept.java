package io.github.yangyouwang.crud.system.entity;

import io.github.yangyouwang.common.domain.BaseTreeEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * <p>
 * 部门表
 * </p>
 *
 * @author yangyouwang
 * @since 2022-09-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="SysDept对象", description="部门表")
public class SysDept extends BaseTreeEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "显示顺序")
    private Integer orderNum;

    @ApiModelProperty(value = "负责人")
    private String leader;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "是否启用 Y 启用 N 禁用")
    private String enabled;
}
