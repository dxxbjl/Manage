package io.github.yangyouwang.crud.app.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.yangyouwang.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * <p>
 * 广告表
 * </p>
 *
 * @author yangyouwang
 * @since 2022-08-11
 */
@Data
@TableName("app_ad")
@ApiModel(value="Ad对象", description="广告表")
public class Ad extends BaseEntity {

    @ApiModelProperty(value = "广告标题")
    private String adTitle;

    @ApiModelProperty(value = "广告宣传图片")
    private String adUrl;

    @ApiModelProperty(value = "活动内容")
    private String adContent;

    @ApiModelProperty(value = "是否启用 Y 启用 N 禁用")
    private String enabled;
}
