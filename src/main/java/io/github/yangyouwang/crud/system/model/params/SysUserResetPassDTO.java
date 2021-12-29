package io.github.yangyouwang.crud.system.model.params;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yangyouwang
 * @title: SysUserResetPassDTO
 * @projectName crud
 * @description: 修改用户密码
 * @date 2021/4/16上午10:17
 */
@Data
public class SysUserResetPassDTO implements Serializable {

    private static final long serialVersionUID = 2034614882841217030L;
    /**
     * 主键id
     */
    @NotNull(message = "id不能为空")
    private Long id;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String passWord;
}
