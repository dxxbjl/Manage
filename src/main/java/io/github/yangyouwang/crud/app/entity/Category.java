package io.github.yangyouwang.crud.app.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.yangyouwang.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 分类表
 * </p>
 *
 * @author yangyouwang
 * @since 2022-10-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("app_category")
@ApiModel(value="Category对象", description="分类表")
public class Category extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "父分类ID")
    private Long parentId;


}
