package io.github.yangyouwang.crud.api.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description: QQ授权DTO<br/>
 * date: 2022/12/4 8:10<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Data
@ApiModel("QQ授权DTO")
public class QQAuthDTO {

    @ApiModelProperty(value = "授权码")
    private String code;
}
