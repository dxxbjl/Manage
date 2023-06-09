package io.github.yangyouwang.module.api.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Description: 用户详情VO对象<br/>
 * date: 2022/8/12 16:35<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Data
@ApiModel("用户详情VO")
public class UserInfoVO {

    @ApiModelProperty(value = "用户昵称或网络名称")
    private String nickName;

    @ApiModelProperty(value = "用户头像图片")
    private String avatar;

    @ApiModelProperty(value = "性别：1时是男性，值为2时是女性，值为0时是未知")
    private Integer gender;

    @ApiModelProperty(value = "性别：1时是男性，值为2时是女性，值为0时是未知")
    private String genderLabel;

    @ApiModelProperty(value = "生日")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date birthday;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    private String email;
}
