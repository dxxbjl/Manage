package io.github.yangyouwang.system.model.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * @author yangyouwang
 * @title: SysUserResp
 * @projectName crud
 * @description: 用户响应
 * @date 2021/3/254:43 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserResp implements Serializable {

    /**
     * 主键id
     */
    private Long id;
    /**
     * 账号
     */
    private String userName;
    /**
     * 启用
     */
    private String enabled;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号码
     */
    private String phonenumber;
    /**
     * 用户性别（0男 1女 2未知）
     */
    private String sex;
    /**
     * 头像
     */
    private String avatar;
}
