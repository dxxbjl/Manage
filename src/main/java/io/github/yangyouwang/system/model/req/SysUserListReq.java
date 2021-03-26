package io.github.yangyouwang.system.model.req;

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
public class SysUserListReq implements Serializable {
    /**
     * pageNum
     */
    private Integer pageNum;
    /**
     * pageSize
     */
    private Integer pageSize;
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
