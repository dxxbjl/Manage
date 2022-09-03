package io.github.yangyouwang.crud.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.github.yangyouwang.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

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
public class SysDept extends BaseEntity {

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

    @ApiModelProperty(value = "父部门ID")
    private Long parentId;

    @ApiModelProperty(value = "是否启用 Y 启用 N 禁用")
    private String enabled;

    /** 子部门 */
    @TableField(exist = false)
    private List<SysDept> children = new ArrayList<>();
    /**
     * 父部门名称
     */
    @TableField(exist = false)
    private String parentName;
}
