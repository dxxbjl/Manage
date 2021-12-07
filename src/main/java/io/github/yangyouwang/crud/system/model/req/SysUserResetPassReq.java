package io.github.yangyouwang.crud.system.model.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yangyouwang
 * @title: SysUserResetPassReq
 * @projectName crud
 * @description: 修改用户密码
 * @date 2021/4/16上午10:17
 */
@Data
public class SysUserResetPassReq implements Serializable {

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
