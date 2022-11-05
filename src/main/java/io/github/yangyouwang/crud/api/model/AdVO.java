package io.github.yangyouwang.crud.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description: 轮播图VO<br/>
 * date: 2022/11/5 17:23<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Data
@ApiModel("轮播图VO")
public class AdVO {
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;
    /**
     * 广告宣传图片
     */
    @ApiModelProperty("广告宣传图片")
    private String url;
}
