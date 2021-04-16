package io.github.yangyouwang.crud.system.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import io.github.yangyouwang.crud.system.dao.SysRoleRepository;
import io.github.yangyouwang.crud.system.dao.SysUserRepository;
import io.github.yangyouwang.crud.system.model.SysRole;
import io.github.yangyouwang.crud.system.model.SysUser;
import io.github.yangyouwang.crud.system.model.req.*;
import io.github.yangyouwang.crud.system.model.resp.SysUserResp;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author yangyouwang
 * @title: SysUserService
 * @projectName crud
 * @description: 用户业务层
 * @date 2021/3/2112:37 AM
 */
@Service
public class SysUserService implements UserDetailsService {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private SysRoleRepository sysRoleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(@NonNull String userName) {
        // 通过用户名从数据库获取用户信息
        SysUser sysUser = sysUserRepository.findByUserName(userName);
        if (Objects.isNull(sysUser)) {
            throw new UsernameNotFoundException("账号不存在");
        }
        return sysUser;
    }

    /**
     * 用户详情
     * @param id 用户id
     * @return 用户详情
     */

    @Transactional(readOnly = true)
    public SysUserResp detail(Long id) {
        SysUser sysUser = sysUserRepository.findById(id).orElse(new SysUser());
        SysUserResp sysUserResp = new SysUserResp();
        BeanUtils.copyProperties(sysUser,sysUserResp);
        Long[] roleIds = sysUser.getRoles().stream().map(s -> s.getId()).toArray(Long[]::new);
        sysUserResp.setRoleIds(roleIds);
        sysUserResp.setEnabled(sysUser.getEnabled());
        return sysUserResp;
    }

    /**
     * 列表请求
     * @return 列表
     */
    @Transactional(readOnly = true)
    public Page<SysUserResp> list(SysUserListReq sysUserListReq) {
        Pageable pageable = PageRequest.of(sysUserListReq.getPageNum() - 1,sysUserListReq.getPageSize());
        return sysUserRepository.findPage(sysUserListReq, pageable);
    }

    /**
     * 添加请求
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    public void add(SysUserAddReq sysUserAddReq) {
        SysUser sysUser = sysUserRepository.findByUserName(sysUserAddReq.getUserName());
        if (Objects.nonNull(sysUser)) {
            throw new RuntimeException("用户已存在");
        }
        sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserAddReq,sysUser);
        String passWord = passwordEncoder.encode(sysUserAddReq.getPassWord());
        sysUser.setPassWord(passWord);
        // 查询角色
        List<SysRole> sysRoles = Arrays.stream(sysUserAddReq.getRoleIds()).map(s -> {
            SysRole sysRole = sysRoleRepository.findById(s).orElse(new SysRole());
            return sysRole;
        }).collect(Collectors.toList());
        sysUser.setRoles(sysRoles);
        sysUserRepository.save(sysUser);
    }

    /**
     * 编辑请求
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    public void edit(SysUserEditReq sysUserEditReq) {
        sysUserRepository.findById(sysUserEditReq.getId()).ifPresent(sysUser -> {
            BeanUtil.copyProperties(sysUserEditReq,sysUser,true, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
            // 查询角色
            Optional.ofNullable(sysUserEditReq.getRoleIds()).ifPresent(ids -> {
                List<SysRole> sysRoles = Arrays.stream(ids).map(s -> {
                    return sysRoleRepository.findById(s).orElse(new SysRole());
                }).collect(Collectors.toList());
                sysUser.setRoles(sysRoles);
            });
            sysUserRepository.save(sysUser);
        });
    }

    /**
     * 删除请求
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    public void del(Long id) {
        if(sysUserRepository.existsById(id)) {
            sysUserRepository.deleteById(id);
        }
    }

    /**
     * 修改密码
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    public void modifyPass(ModifyPassReq modifyPassReq) {
        SysUser sysUser = sysUserRepository.findById(modifyPassReq.getId()).orElse(new SysUser());
        boolean matches = passwordEncoder.matches(modifyPassReq.getOldPassword(),sysUser.getPassword());
        if (!matches) {
            throw new RuntimeException("旧密码输入错误");
        }
        String password = passwordEncoder.encode(modifyPassReq.getPassword());
        sysUser.setPassWord(password);
        sysUserRepository.save(sysUser);
    }

    /**
     * 修改用户状态
     * @param sysUserEnabledReq 用户状态dto
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    public void changeUser(SysUserEnabledReq sysUserEnabledReq) {
        sysUserRepository.findById(sysUserEnabledReq.getId()).ifPresent(sysUser -> {
            sysUser.setEnabled(sysUserEnabledReq.getEnabled());
            sysUserRepository.save(sysUser);
        });

    }
}