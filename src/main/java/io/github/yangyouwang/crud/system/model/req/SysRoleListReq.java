package io.github.yangyouwang.crud.system.model.req;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * @author yangyouwang
 * @title: SysUserAddReq
 * @projectName crud
 * @description: 用户请求
 * @date 2021/3/269:56 AM
 */
@Data
public class SysRoleListReq implements Serializable {
    private static final long serialVersionUID = -650252266859884454L;
    /**
     * pageNum
     */
    @NotNull(message = "pageNum不能为空")
    private Integer pageNum;
    /**
     * pageSize
     */
    @NotNull(message = "pageSize不能为空")
    private Integer pageSize;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色标识
     */
    private String roleKey;
}
