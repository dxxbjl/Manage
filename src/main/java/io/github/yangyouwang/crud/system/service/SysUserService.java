package io.github.yangyouwang.crud.system.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.yangyouwang.common.constant.Constants;
import io.github.yangyouwang.core.exception.CrudException;
import io.github.yangyouwang.crud.system.mapper.SysMenuMapper;
import io.github.yangyouwang.crud.system.mapper.SysUserMapper;
import io.github.yangyouwang.crud.system.mapper.SysUserRoleMapper;
import io.github.yangyouwang.crud.system.model.*;
import io.github.yangyouwang.crud.system.model.req.*;
import io.github.yangyouwang.crud.system.model.resp.SysUserResp;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User;
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
        Set<GrantedAuthority> authorities = new LinkedHashSet<>();
        if (CollectionUtils.isNotEmpty(user.getRoles())) {
            for (SysRole role : user.getRoles()) {
                List<SysMenu> menus = sysMenuMapper.findMenuByRoleId(role.getId());
                for (SysMenu menu : menus) {
                    authorities.add(new SimpleGrantedAuthority(menu.getPerms()));
                }
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleKey()));
            }
        }
        return new User(user.getUserName(), user.getPassWord(), Constants.ENABLED_YES.equals(user.getEnabled()),
                true, true, true, authorities);
    }

    /**
     * 用户详情
     * @param id 用户id
     * @return 用户详情
     */
    @Transactional(readOnly = true)
    public SysUserResp detail(Long id) {
        SysUser sysUser = sysUserMapper.findUserByUserId(id);
        SysUserResp sysUserResp = new SysUserResp();
        BeanUtils.copyProperties(sysUser,sysUserResp);
        ofNullable(sysUser.getRoles()).ifPresent(sysRoles -> {
            Long[] roleIds = sysRoles.stream().map(SysRole::getId).toArray(Long[]::new);
            sysUserResp.setRoleIds(roleIds);
        });
        return sysUserResp;
    }

    /**
     * 列表请求
     * @return 列表
     */
    @Transactional(readOnly = true)
    public IPage<SysUser> list(SysUserListReq sysUserListReq) {
        return sysUserMapper.selectPage(new Page<>(sysUserListReq.getPageNum(), sysUserListReq.getPageSize()),
                new LambdaQueryWrapper<SysUser>()
                        .like(StringUtils.isNotBlank(sysUserListReq.getUserName()), SysUser::getUserName , sysUserListReq.getUserName())
                        .like(StringUtils.isNotBlank(sysUserListReq.getEmail()), SysUser::getEmail , sysUserListReq.getEmail())
                        .like(StringUtils.isNotBlank(sysUserListReq.getPhonenumber()), SysUser::getPhonenumber , sysUserListReq.getPhonenumber()));
    }

    /**
     * 添加请求
     * @return 添加状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public int add(SysUserAddReq sysUserAddReq) {
        SysUser sysUser = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUserName,sysUserAddReq.getUserName()));
        Assert.isNull(sysUser,"用户已存在");
        SysUser user = new SysUser();
        BeanUtils.copyProperties(sysUserAddReq,user);
        String passWord = passwordEncoder.encode(sysUserAddReq.getPassWord());
        user.setPassWord(passWord);
        int flag = sysUserMapper.insert(user);
        if (flag > 0) {
            insertUserRoleBatch(user.getId(), sysUserAddReq.getRoleIds());
            return flag;
        }
        throw new CrudException("新增用户失败");
    }

    /**
     * 编辑请求
     * @return 编辑状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public int edit(SysUserEditReq sysUserEditReq) {
        SysUser user = new SysUser();
        BeanUtil.copyProperties(sysUserEditReq,user,true, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        int flag = sysUserMapper.updateById(user);
        if (flag > 0) {
            if (sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>()
                    .eq(SysUserRole::getUserId, user.getId())) > 0) {
                insertUserRoleBatch(user.getId(), sysUserEditReq.getRoleIds());
            }
            return flag;
        }
        throw new CrudException("修改用户失败");
    }

    /**
     * 编辑请求
     * @return 编辑状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public int editUserInfo(SysUserEditUserInfoReq sysUserEditUserInfoReq) {
        SysUser user = new SysUser();
        BeanUtil.copyProperties(sysUserEditUserInfoReq,user,true, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        return sysUserMapper.updateById(user);
    }

    /**
     * 批量新增修改用户关联角色
     * @param userId 用户id
     * @param roleIds 角色id
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void insertUserRoleBatch(Long userId, Long[] roleIds) {
        List<SysUserRole> userRoles = Arrays.stream(roleIds).map(s -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(s);
            return userRole;
        }).collect(Collectors.toList());
        int flag = sysUserRoleMapper.insertBatchSomeColumn(userRoles);
        if (flag == 0) {
            throw new CrudException("批量新增修改用户关联角色失败");
        }
    }

    /**
     * 删除请求
     * @return 删除状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public int del(Long id) {
        int flag = sysUserMapper.deleteById(id);
        if (flag > 0) {
            sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>()
                    .eq(SysUserRole::getUserId,id));
            return flag;
        }
        throw new CrudException("删除用户失败");
    }

    /**
     * 修改密码
     * @return 修改状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public int modifyPass(ModifyPassReq modifyPassReq) {
        SysUser sysUser = sysUserMapper.selectById(modifyPassReq.getId());
        boolean matches = passwordEncoder.matches(modifyPassReq.getOldPassword(),sysUser.getPassWord());
        Assert.isTrue(matches,"旧密码输入错误");
        String password = passwordEncoder.encode(modifyPassReq.getPassword());
        sysUser.setPassWord(password);
        return sysUserMapper.updateById(sysUser);
    }

    /**
     * 修改用户状态
     * @param sysUserEnabledReq 用户状态dto
     * @return 修改状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public int changeUser(SysUserEnabledReq sysUserEnabledReq) {
        SysUser sysUser = new SysUser();
        sysUser.setId(sysUserEnabledReq.getId());
        sysUser.setEnabled(sysUserEnabledReq.getEnabled());
        return sysUserMapper.updateById(sysUser);
    }

    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 用户信息
     */
    @Transactional(readOnly = true)
    public SysUserResp findUserByName(String username) {
        SysUser sysUser = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserName, username));
        SysUserResp sysUserResp = new SysUserResp();
        BeanUtils.copyProperties(sysUser,sysUserResp);
        return sysUserResp;
    }
}