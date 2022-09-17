package io.github.yangyouwang.crud.system.service;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.yangyouwang.common.constant.ConfigConsts;
import io.github.yangyouwang.common.enums.ResultStatus;
import io.github.yangyouwang.core.exception.CrudException;
import io.github.yangyouwang.core.util.StringUtil;
import io.github.yangyouwang.crud.system.entity.*;
import io.github.yangyouwang.crud.system.mapper.SysMenuMapper;
import io.github.yangyouwang.crud.system.mapper.SysUserMapper;
import io.github.yangyouwang.crud.system.mapper.SysUserPostMapper;
import io.github.yangyouwang.crud.system.mapper.SysUserRoleMapper;
import io.github.yangyouwang.crud.system.model.ModifyPassDTO;
import io.github.yangyouwang.crud.system.model.ResetPassDTO;
import io.github.yangyouwang.crud.system.model.SysUserDTO;
import org.springframework.aop.framework.AopContext;
import org.springframework.lang.NonNull;
import org.springframework.security.core.authority.AuthorityUtils;
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
import java.util.stream.Stream;


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
    private SysUserPostMapper sysUserPostMapper;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(@NonNull String userName) {
        // 通过用户名从数据库获取用户信息
        SysUser user = sysUserMapper.findUserByName(userName);
        if (ObjectUtil.isNull(user)) {
            throw new UsernameNotFoundException(ResultStatus.LOGIN_ERROR.message);
        }
        List<String> menuRole;
        if (ConfigConsts.ADMIN_USER.equals(userName)) {
            menuRole = sysMenuMapper.findMenuRole();
        } else {
            menuRole = Stream.of(StringUtil.getId(user.getRoleIds())).map(roleId -> {
                List<String> userMenuRole = sysMenuMapper.findMenuRoleByRoleId(roleId);
                return String.join(",", userMenuRole);
            }).collect(Collectors.toList());
        }
        return new User(user.getUserName(), user.getPassWord(), ConfigConsts.ENABLED_YES.equals(user.getEnabled()),
                true, true, true, AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",",menuRole)));
    }

    /**
     * 用户详情
     * @param id 用户id
     * @return 用户详情
     */
    @Transactional(readOnly = true)
    public SysUser detail(Long id) {
        return sysUserMapper.findUserByUserId(id);
    }

    /**
     * 添加请求
     * @param sysUser 添加用户对象
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public String add(SysUser sysUser) {
        SysUser old = this.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserName,sysUser.getUserName()));
        Assert.isNull(old,ResultStatus.USER_EXIST_ERROR.message);
        String passWord = passwordEncoder.encode(ConfigConsts.DEFAULT_PASSWORD);
        sysUser.setPassWord(passWord);
        boolean flag = this.save(sysUser);
        if (flag) {
            SysUserService proxy = (SysUserService) AopContext.currentProxy();
            proxy.insertUserRoleBatch(sysUser.getId(), StringUtil.getId(sysUser.getRoleIds()));
            proxy.insertUserPostBatch(sysUser.getId(), StringUtil.getId(sysUser.getPostIds()));
        }
        return String.format("初始化密码为：%s",ConfigConsts.DEFAULT_PASSWORD);
    }

    /**
     * 编辑请求
     * @param sysUser 编辑用户对象
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public void edit(SysUser sysUser) {
        boolean flag = this.updateById(sysUser);
        if (flag) {
            SysUserService proxy = (SysUserService) AopContext.currentProxy();
            // 删除用户关联角色
            sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, sysUser.getId()));
            proxy.insertUserRoleBatch(sysUser.getId(), StringUtil.getId(sysUser.getRoleIds()));
            // 删除用户关联岗位
            sysUserPostMapper.delete(new LambdaQueryWrapper<SysUserPost>().eq(SysUserPost::getUserId, sysUser.getId()));
            proxy.insertUserPostBatch(sysUser.getId(), StringUtil.getId(sysUser.getPostIds()));
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
     * 批量新增修改用户关联岗位
     * @param userId 用户id
     * @param postIds 岗位id
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public void insertUserPostBatch(Long userId, Long[] postIds) {
        if (postIds.length > 0) {
            List<SysUserPost> userPosts = Arrays.stream(postIds).map(s -> {
                SysUserPost userPost = new SysUserPost();
                userPost.setUserId(userId);
                userPost.setPostId(s);
                return userPost;
            }).collect(Collectors.toList());
            sysUserPostMapper.insertBatchSomeColumn(userPosts);
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
            sysUserPostMapper.delete(new LambdaQueryWrapper<SysUserPost>().eq(SysUserPost::getUserId,id));
        }
    }
    /**
     * 导出用户列表
     * @return 用户列表
     */
    @Transactional(readOnly = true)
    public List<SysUserDTO> exportSysUserList() {
        return sysUserMapper.findUserList();
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
     * @param resetPassDTO 重置用户密码对象
     * @return 提示
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public String resetPass(ResetPassDTO resetPassDTO) {
        SysUser sysUser = this.getById(resetPassDTO.getId());
        if (Objects.isNull(sysUser)) {
            throw new CrudException(ResultStatus.USER_NO_EXIST_ERROR);
        }
        String password = passwordEncoder.encode(ConfigConsts.DEFAULT_PASSWORD);
        sysUser.setPassWord(password);
        this.updateById(sysUser);
        return String.format("密码重置为：%s",ConfigConsts.DEFAULT_PASSWORD);
    }
}