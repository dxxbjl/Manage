package io.github.yangyouwang.crud.app.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.yangyouwang.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 通知公告表
 * </p>
 *
 * @author yangyouwang
 * @since 2022-10-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("app_notice")
@ApiModel(value="Notice对象", description="通知公告表")
public class Notice extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标题")
    private String noticeTitle;

    @ApiModelProperty(value = "公告类型（1通知 2公告）")
    private String noticeType;

    @ApiModelProperty(value = "公告状态（0正常 1关闭）")
    private String noticeStatus;

    @ApiModelProperty(value = "内容")
    private String noticeContent;


}
