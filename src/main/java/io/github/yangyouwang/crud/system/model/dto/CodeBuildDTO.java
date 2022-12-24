package io.github.yangyouwang.crud.system.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * Description: 代码生成DTO <br/>
 * date: 2022/8/30 23:23<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Data
@ApiModel("代码生成DTO")
public class CodeBuildDTO {
    /**
     * 模块名
     */
    @ApiModelProperty("模块名")
    private String moduleName;
    /**
     * 表名
     */
    @ApiModelProperty("表名")
    private String tables;
    /**
     * 菜单ID
     */
    @ApiModelProperty("菜单ID")
    private Long menuId;
}
