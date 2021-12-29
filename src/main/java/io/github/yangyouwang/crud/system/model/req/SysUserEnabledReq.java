package io.github.yangyouwang.crud.system.model.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yangyouwang
 * @title: SysUserEnabledReq
 * @projectName crud
 * @description: 用户启用状态
 * @date 2021/4/16上午10:17
 */
@Data
public class SysUserEnabledReq implements Serializable {

    private static final long serialVersionUID = -1514676296852041028L;
    /**
     * 主键id
     */
    @NotNull(message = "id不能为空")
    private Long id;
    /**
     * 启用
     */
    @NotBlank(message = "启用不能为空")
    private String enabled;
}
