package io.github.yangyouwang.crud.system.model.params;

import io.github.yangyouwang.common.domain.BasePageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


/**
 * @author yangyouwang
 * @title: SysUserListDTO
 * @projectName crud
 * @description: 用户请求
 * @date 2021/3/269:56 AM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserListDTO extends BasePageDTO implements Serializable {
    private static final long serialVersionUID = -743255883030372538L;
    /**
     * 账号
     */
    private String userName;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号码
     */
    private String phonenumber;
}
