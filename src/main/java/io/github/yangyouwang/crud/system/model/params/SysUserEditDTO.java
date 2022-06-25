package io.github.yangyouwang.crud.system.model.params;

import io.github.yangyouwang.common.domain.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author yangyouwang
 * @title: SysUserEditDTO
 * @projectName crud
 * @description: 用户编辑
 * @date 2021/3/269:56 AM
 */
@Data
public class SysUserEditDTO extends BaseEntity {

    private static final long serialVersionUID = -7564648683856200495L;
    /**
     * 主键id
     */
    @NotNull(message = "id不能为空")
    private Long id;
    /**
     * 启用
     */
    private String enabled;
    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    private String email;
    /**
     * 手机号码
     */
    @NotBlank(message = "手机号码不能为空")
    private String phonenumber;
    /**
     * 用户性别（0男 1女 2未知）
     */
    @NotBlank(message = "用户性别不能为空")
    private String sex;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 角色id
     */
    @NotEmpty(message = "角色id不能为空")
    private Long[] roleIds;
}
