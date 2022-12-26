package io.github.yangyouwang.crud.system.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author yangyouwang
 * @title: ModifyPassDTO
 * @projectName crud
 * @description: 修改密码
 * @date 2021/3/312:55 PM
 */
@Data
@ApiModel("修改密码")
public class ModifyPassDTO {
    /**
     * 主键id
     */
    @NotNull(message = "id不能为空")
    private Long id;
    /**
     * 当前密码
     */
    @NotBlank(message = "当前密码不能为空")
    private String oldPassword;
    /**
     * 新密码
     */
    @NotBlank(message = "新密码不能为空")
    private String password;
}
