package io.github.yangyouwang.crud.qrtz.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.yangyouwang.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * <p>
 * 任务日志
 * </p>
 *
 * @author yangyouwang
 * @since 2022-10-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("qrtz_job_log")
@ApiModel(value="JobLog对象", description="任务日志")
public class JobLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "任务组名")
    private String taskGroup;

    @ApiModelProperty(value = "调用目标字符串")
    private String invokeTarget;

    @ApiModelProperty(value = "调用目标参数")
    private String invokeParam;

    @ApiModelProperty(value = "日志信息")
    private String taskMessage;

    @ApiModelProperty(value = "执行状态：0 正常、1 失败")
    private String status;

    @ApiModelProperty(value = "异常信息")
    private String exceptionInfo;
}
