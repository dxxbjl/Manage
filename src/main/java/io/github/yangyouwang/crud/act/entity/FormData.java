package io.github.yangyouwang.crud.act.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.yangyouwang.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 表单
 * </p>
 *
 * @author yangyouwang
 * @since 2022-08-31
 */
@Data
@TableName("act_form_data")
@ApiModel(value="Form对象", description="表单")
public class FormData extends BaseEntity {

    @ApiModelProperty(value = "表单名称")
    private String formName;

    @ApiModelProperty(value = "表单KEY")
    private String formKey;

    @ApiModelProperty(value = "表单XML数据")
    private String formXmlData;

    @ApiModelProperty(value = "表单JSON数据")
    private String formJsonData;
}
