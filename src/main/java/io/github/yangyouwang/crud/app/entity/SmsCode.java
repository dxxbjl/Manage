package io.github.yangyouwang.crud.app.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 短信验证码表
 * </p>
 *
 * @author yangyouwang
 * @since 2022-08-27
 */
@Data
@TableName("app_sms_code")
@ApiModel(value="SmsCode对象", description="短信验证码表")
public class SmsCode {

    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "验证码")
    private String code;

    @ApiModelProperty(value = "失效时间")
    private Date deadLine;

    @ApiModelProperty(value = "是否有效，1-无效，2-有效")
    private Integer usable;

    @ApiModelProperty(value = "是否已发送，1-未发送，2-已发送")
    private Integer sended;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;
}
