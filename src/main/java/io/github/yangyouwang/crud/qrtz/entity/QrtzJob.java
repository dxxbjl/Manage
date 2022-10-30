package io.github.yangyouwang.crud.qrtz.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.yangyouwang.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 任务表
 * </p>
 *
 * @author yangyouwang
 * @since 2022-07-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("qrtz_job")
@ApiModel(value="Job对象", description="任务表")
public class QrtzJob extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务名字")
    private String jobName;

    @ApiModelProperty(value = "任务组")
    private String jobGroup;

    @ApiModelProperty(value = "cron表达式")
    private String cron;

    @ApiModelProperty(value = "类名称")
    private String jobClassName;

    @TableField(exist = false)
    @ApiModelProperty(value = "任务状态")
    private String triggerState;
}
