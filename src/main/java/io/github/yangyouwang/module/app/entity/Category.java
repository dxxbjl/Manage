package io.github.yangyouwang.module.app.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.yangyouwang.common.domain.BaseTreeEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 分类表
 * </p>
 *
 * @author yangyouwang
 * @since 2022-10-30
 */
@Data
@TableName("app_category")
@ApiModel(value="Category对象", description="分类表")
public class Category extends BaseTreeEntity {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "顺序")
    private String orderNum;
}
