package io.github.yangyouwang.module.system.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description: 代码配置VO <br/>
 * date: 2022/8/30 23:23<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Data
@ApiModel("代码配置VO")
public class CodeConfigVO {

    /**
     * 数据库连接地址
     */
    @ApiModelProperty("数据库连接地址")
    private String url;
    /**
     * 数据库驱动
     */
    @ApiModelProperty("数据库驱动")
    private String driverName;
    /**
     * 数据库账号
     */
    @ApiModelProperty("数据库账号")
    private String username;
    /**
     * 数据库密码
     */
    @ApiModelProperty("数据库密码")
    private String password;
    /**
     * 作者
     */
    @ApiModelProperty("作者")
    private String author;
}
