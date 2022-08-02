package io.github.yangyouwang.crud.system.service;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.yangyouwang.common.constant.ConfigConsts;
import io.github.yangyouwang.common.enums.ResultStatus;
import io.github.yangyouwang.crud.system.entity.SysMenu;
import io.github.yangyouwang.crud.system.entity.SysRole;
import io.github.yangyouwang.crud.system.entity.SysUser;
import io.github.yangyouwang.crud.system.entity.SysUserRole;
import io.github.yangyouwang.crud.system.mapper.SysMenuMapper;
import io.github.yangyouwang.crud.system.mapper.SysUserMapper;
import io.github.yangyouwang.crud.system.mapper.SysUserRoleMapper;
import io.github.yangyouwang.crud.system.model.ModifyPassDTO;
import io.github.yangyouwang.crud.system.model.SysUserDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Optional.*;

/**
 * @author yangyouwang
 * @title: SysUserService
 * @projectName crud
 * @description: 用户业务层
 * @date 2021/3/2112:37 AM
 */
@Service
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> implements UserDetailsService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(@NonNull String userName) {
        // 通过用户名从数据库获取用户信息
        SysUser user  = sysUserMapper.findUserByName(userName);
        if (ObjectUtil.isNull(user)) {
            throw new UsernameNotFoundException(ResultStatus.LOGIN_ERROR.message);
        }
        Set<GrantedAuthority> authorities = new LinkedHashSet<>();
        for (SysRole role : user.getRoles()) {
            List<SysMenu> menus = sysMenuMapper.findMenuByRoleId(role.getId());
            for (SysMenu menu : menus) {
                authorities.add(new SimpleGrantedAuthority(menu.getPerms()));
            }
            authorities.add(new SimpleGrantedAuthority(ConfigConsts.ROLE_PREFIX + role.getRoleKey()));
        }
        return new User(user.getUserName(), user.getPassWord(), ConfigConsts.ENABLED_YES.equals(user.getEnabled()),
                true, true, true, authorities);
    }

    /**
     * 用户详情
     * @param id 用户id
     * @return 用户详情
     */
    @Transactional(readOnly = true)
    public SysUser detail(Long id) {
        SysUser sysUser = sysUserMapper.findUserByUserId(id);
        ofNullable(sysUser.getRoles()).ifPresent(sysRoles -> {
            Long[] roleIds = sysRoles.stream().map(SysRole::getId).toArray(Long[]::new);
            sysUser.setRoleIds(roleIds);
        });
        return sysUser;
    }

    /**
     * 列表请求
     * @param sysUser 用户列表对象
     * @return 列表
     */
    @Transactional(readOnly = true)
    public List<SysUser> list(SysUser sysUser) {
        return this.list(new LambdaQueryWrapper<SysUser>()
                        .like(StringUtils.isNotBlank(sysUser.getUserName()), SysUser::getUserName , sysUser.getUserName())
                        .like(StringUtils.isNotBlank(sysUser.getEmail()), SysUser::getEmail , sysUser.getEmail())
                        .like(StringUtils.isNotBlank(sysUser.getPhonenumber()), SysUser::getPhonenumber , sysUser.getPhonenumber()));
    }

    /**
     * 添加请求
     * @param sysUser 添加用户对象
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public void add(SysUser sysUser) {
        SysUser old = this.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserName,sysUser.getUserName()));
        Assert.isNull(old,ResultStatus.USER_EXIST_ERROR.message);
        String passWord = passwordEncoder.encode(sysUser.getPassWord());
        sysUser.setPassWord(passWord);
        boolean flag = this.save(sysUser);
        if (flag) {
            SysUserService proxy = (SysUserService) AopContext.currentProxy();
            proxy.insertUserRoleBatch(sysUser.getId(), sysUser.getRoleIds());
        }
    }

    /**
     * 编辑请求
     * @param sysUser 编辑用户对象
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public void edit(SysUser sysUser) {
        boolean flag = this.updateById(sysUser);
        if (flag && sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, sysUser.getId())) > 0) {
            SysUserService proxy = (SysUserService) AopContext.currentProxy();
            proxy.insertUserRoleBatch(sysUser.getId(), sysUser.getRoleIds());
        }
    }

    /**
     * 批量新增修改用户关联角色
     * @param userId 用户id
     * @param roleIds 角色id
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public void insertUserRoleBatch(Long userId, Long[] roleIds) {
        if (roleIds.length > 0) {
            List<SysUserRole> userRoles = Arrays.stream(roleIds).map(s -> {
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(s);
                return userRole;
            }).collect(Collectors.toList());
            sysUserRoleMapper.insertBatchSomeColumn(userRoles);
        }
    }

    /**
     * 删除请求
     * @param id 用户id
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public void del(Long id) {
        boolean flag = this.removeById(id);
        if (flag) {
            sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId,id));
        }
    }
    /**
     * 导出用户列表
     * @return 用户列表
     */
    @Transactional(readOnly = true)
    public List<SysUserDTO> exportSysUserList() {
        List<SysUser> sysUsers = this.list(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getEnabled, ConfigConsts.ENABLED_YES));
        return sysUsers.stream().map(sysUser -> {
            SysUserDTO sysUserDTO = new SysUserDTO();
            BeanUtils.copyProperties(sysUser, sysUserDTO);
            return sysUserDTO;
        }).collect(Collectors.toList());
    }
    /**
     * 修改密码
     * @param modifyPassDTO 修改密码对象
     * @return 修改状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public boolean modifyPass(ModifyPassDTO modifyPassDTO) {
        SysUser sysUser = this.getById(modifyPassDTO.getId());
        boolean matches = passwordEncoder.matches(modifyPassDTO.getOldPassword(),sysUser.getPassWord());
        Assert.isTrue(matches,ResultStatus.OLD_PASSWORD_ERROR.message);
        String password = passwordEncoder.encode(modifyPassDTO.getPassword());
        sysUser.setPassWord(password);
        return this.updateById(sysUser);
    }

    /**
     * 重置密码
     * @param sysUser 重置用户密码对象
     * @return 重置密码
     */
    public boolean resetPass(SysUser sysUser) {
        String password = passwordEncoder.encode(sysUser.getPassWord());
        sysUser.setId(sysUser.getId());
        sysUser.setPassWord(password);
        return this.updateById(sysUser);
    }
}