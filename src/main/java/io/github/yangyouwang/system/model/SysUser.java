package io.github.yangyouwang.system.model;

import io.github.yangyouwang.common.constant.Constants;
import io.github.yangyouwang.common.domain.BaseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author yangyouwang
 * @title: SysUser
 * @projectName crud
 * @description: 用户类
 * @date 2021/3/2112:22 AM
 */
@Entity
@Table(name="sys_user")
public class SysUser extends BaseEntity implements UserDetails  {

    private static final long serialVersionUID = 1960027566696794847L;
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    /**
     * 账号
     */
    @Column(name="user_name")
    private String userName;
    /**
     * 密码
     */
    @Column(name="pass_word")
    private String passWord;
    /**
     * 启用
     */
    @Column(name="enabled")
    private String enabled;
    /**
     * 邮箱
     */
    @Column(name="email")
    private String email;
    /**
     * 手机号码
     */
    @Column(name="phonenumber")
    private String phonenumber;
    /**
     * 用户性别（0男 1女 2未知）
     */
    @Column(name="sex")
    private String sex;
    /**
     * 头像
     */
    @Column(name="avatar")
    private String avatar;

    /**
     * 多个用户对应多个角色
     */
    @ManyToMany(targetEntity = SysRole.class,fetch = FetchType.EAGER)
    @JoinTable(name = "sys_user_role",
            joinColumns = @JoinColumn(name="user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"))
    private List<SysRole> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (SysRole role : this.roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleKey()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.passWord;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Constants.ENABLED_YES.equals(this.enabled);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }
}
