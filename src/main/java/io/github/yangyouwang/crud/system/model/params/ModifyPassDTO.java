package io.github.yangyouwang.crud.system.model.params;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yangyouwang
 * @title: ModifyPassDTO
 * @projectName crud
 * @description: 修改密码
 * @date 2021/3/312:55 PM
 */
@Data
public class ModifyPassDTO implements Serializable {

    private static final long serialVersionUID = -2784032352485715834L;
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
