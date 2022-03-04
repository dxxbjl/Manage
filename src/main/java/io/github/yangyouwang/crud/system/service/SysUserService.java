package io.github.yangyouwang.crud.system.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import io.github.yangyouwang.crud.system.model.params.*;
import io.github.yangyouwang.crud.system.model.result.SysUserDTO;
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
    public SysUserDTO detail(Long id) {
        SysUser sysUser = sysUserMapper.findUserByUserId(id);
        SysUserDTO sysUserDTO = new SysUserDTO();
        BeanUtils.copyProperties(sysUser,sysUserDTO);
        ofNullable(sysUser.getRoles()).ifPresent(sysRoles -> {
            Long[] roleIds = sysRoles.stream().map(SysRole::getId).toArray(Long[]::new);
            sysUserDTO.setRoleIds(roleIds);
        });
        return sysUserDTO;
    }

    /**
     * 列表请求
     * @param sysUserListDTO 用户列表对象
     * @return 列表
     */
    @Transactional(readOnly = true)
    public IPage list(SysUserListDTO sysUserListDTO) {
        return this.page(new Page<>(sysUserListDTO.getPageNum(), sysUserListDTO.getPageSize()),
                new LambdaQueryWrapper<SysUser>()
                        .like(StringUtils.isNotBlank(sysUserListDTO.getUserName()), SysUser::getUserName , sysUserListDTO.getUserName())
                        .like(StringUtils.isNotBlank(sysUserListDTO.getEmail()), SysUser::getEmail , sysUserListDTO.getEmail())
                        .like(StringUtils.isNotBlank(sysUserListDTO.getPhonenumber()), SysUser::getPhonenumber , sysUserListDTO.getPhonenumber()));
    }

    /**
     * 添加请求
     * @param sysUserAddDTO 添加用户对象
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public void add(SysUserAddDTO sysUserAddDTO) {
        SysUser sysUser = this.getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUserName,sysUserAddDTO.getUserName()));
        Assert.isNull(sysUser,ResultStatus.USER_EXIST_ERROR.message);
        SysUser user = new SysUser();
        BeanUtils.copyProperties(sysUserAddDTO,user);
        String passWord = passwordEncoder.encode(sysUserAddDTO.getPassWord());
        user.setPassWord(passWord);
        boolean flag = this.save(user);
        if (flag) {
            SysUserService proxy = (SysUserService) AopContext.currentProxy();
            proxy.insertUserRoleBatch(user.getId(), sysUserAddDTO.getRoleIds());
        }
    }

    /**
     * 编辑请求
     * @param sysUserEditDTO 编辑用户对象
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public void edit(SysUserEditDTO sysUserEditDTO) {
        SysUser user = new SysUser();
        BeanUtil.copyProperties(sysUserEditDTO,user,true, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        boolean flag = this.updateById(user);
        if (flag && sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getUserId, user.getId())) > 0) {
            SysUserService proxy = (SysUserService) AopContext.currentProxy();
            proxy.insertUserRoleBatch(user.getId(), sysUserEditDTO.getRoleIds());
        }
    }

    /**
     * 编辑请求
     * @param sysUserEditUserInfoDTO 编辑用户对象
     * @return 编辑状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public boolean editUserInfo(SysUserEditUserInfoDTO sysUserEditUserInfoDTO) {
        SysUser user = new SysUser();
        BeanUtil.copyProperties(sysUserEditUserInfoDTO,user,true, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        return this.updateById(user);
    }

    /**
     * 批量新增修改用户关联角色
     * @param userId 用户id
     * @param roleIds 角色id
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public void insertUserRoleBatch(Long userId, Long[] roleIds) {
        List<SysUserRole> userRoles = Arrays.stream(roleIds).map(s -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(s);
            return userRole;
        }).collect(Collectors.toList());
        sysUserRoleMapper.insertBatchSomeColumn(userRoles);
    }

    /**
     * 删除请求
     * @param id 用户id
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public void del(Long id) {
        this.removeById(id);
        sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getUserId,id));
    }

    /**
     * 修改用户状态
     * @param sysUserEnabledDTO 用户状态dto
     * @return 修改状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public boolean changeUser(SysUserEnabledDTO sysUserEnabledDTO) {
        SysUser sysUser = new SysUser();
        sysUser.setId(sysUserEnabledDTO.getId());
        sysUser.setEnabled(sysUserEnabledDTO.getEnabled());
        return this.updateById(sysUser);
    }

    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 用户信息
     */
    @Transactional(readOnly = true)
    public SysUserDTO findUserByName(String username) {
        SysUser sysUser = this.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserName, username));
        SysUserDTO sysUserDTO = new SysUserDTO();
        BeanUtils.copyProperties(sysUser,sysUserDTO);
        return sysUserDTO;
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
     * @param sysUserResetPassDTO 重置用户密码对象
     * @return 重置密码
     */
    public boolean resetPass(SysUserResetPassDTO sysUserResetPassDTO) {
        SysUser sysUser = new SysUser();
        String password = passwordEncoder.encode(sysUserResetPassDTO.getPassWord());
        sysUser.setId(sysUserResetPassDTO.getId());
        sysUser.setPassWord(password);
        return this.updateById(sysUser);
    }
}