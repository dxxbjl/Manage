package io.github.yangyouwang.crud.app.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.yangyouwang.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 新闻表
 * </p>
 *
 * @author yangyouwang
 * @since 2022-11-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("app_news")
@ApiModel(value="News对象", description="新闻表")
public class News extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "缩略图")
    private String thumbnail;

    @ApiModelProperty(value = "内容")
    private String content;
}