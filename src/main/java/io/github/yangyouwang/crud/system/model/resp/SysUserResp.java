package io.github.yangyouwang.crud.system.model.resp;

import lombok.Data;

import java.io.Serializable;


/**
 * @author yangyouwang
 * @title: SysUserResp
 * @projectName crud
 * @description: 用户响应
 * @date 2021/3/254:43 PM
 */
@Data
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
    /** 备注 */
    private String remark;

    /**
     * 角色id
     */
    private Long[] roleIds;

    public SysUserResp() {
    }

    public SysUserResp(Long id, String userName, String enabled, String email, String phonenumber, String sex, String avatar, String remark) {
        this.id = id;
        this.userName = userName;
        this.enabled = enabled;
        this.email = email;
        this.phonenumber = phonenumber;
        this.sex = sex;
        this.avatar = avatar;
        this.remark = remark;
    }
}
