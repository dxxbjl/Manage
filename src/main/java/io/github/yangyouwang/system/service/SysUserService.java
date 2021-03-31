package io.github.yangyouwang.system.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import io.github.yangyouwang.system.dao.SysRoleRepository;
import io.github.yangyouwang.system.dao.SysUserRepository;
import io.github.yangyouwang.system.model.SysRole;
import io.github.yangyouwang.system.model.SysUser;
import io.github.yangyouwang.system.model.req.ModifyPassReq;
import io.github.yangyouwang.system.model.req.SysUserAddReq;
import io.github.yangyouwang.system.model.req.SysUserEditReq;
import io.github.yangyouwang.system.model.req.SysUserListReq;
import io.github.yangyouwang.system.model.resp.SysUserResp;
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
import javax.transaction.Transactional;
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
@Transactional
public class SysUserService implements UserDetailsService {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private SysRoleRepository sysRoleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(@NonNull String userName) throws UsernameNotFoundException {
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
    public SysUserResp detail(Long id) {
        SysUser sysUser = sysUserRepository.findById(id).orElse(new SysUser());
        SysUserResp sysUserResp = new SysUserResp();
        BeanUtil.copyProperties(sysUser,sysUserResp,true, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        Long[] roleIds = sysUser.getRoles().stream().map(s -> s.getId()).toArray(Long[]::new);
        sysUserResp.setRoleIds(roleIds);
        sysUserResp.setEnabled(sysUser.getEnabled());
        return sysUserResp;
    }

    /**
     * 列表请求
     * @return 列表
     */
    public Page<SysUserResp> list(SysUserListReq sysUserListReq) {
        Pageable pageable = PageRequest.of(sysUserListReq.getPageNum() - 1,sysUserListReq.getPageSize());
        return sysUserRepository.findPage(sysUserListReq, pageable);
    }

    /**
     * 添加请求
     * @return 添加状态
     */
    public void add(SysUserAddReq sysUserAddReq) {
        SysUser sysUser = sysUserRepository.findByUserName(sysUserAddReq.getUserName());
        if (Objects.nonNull(sysUser)) {
            throw new RuntimeException("用户已存在");
        }
        sysUser = new SysUser();
        BeanUtil.copyProperties(sysUserAddReq,sysUser,true, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
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
     * @return 编辑状态
     */
    public void edit(SysUserEditReq sysUserEditReq) {
        SysUser sysUser = sysUserRepository.findById(sysUserEditReq.getId()).get();
        BeanUtil.copyProperties(sysUserEditReq,sysUser,true, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        // 查询角色
        Optional.ofNullable(sysUserEditReq.getRoleIds()).ifPresent(ids -> {
            List<SysRole> sysRoles = Arrays.stream(ids).map(s -> {
                SysRole sysRole = sysRoleRepository.findById(s).orElse(new SysRole());
                return sysRole;
            }).collect(Collectors.toList());
            sysUser.setRoles(sysRoles);
        });
        sysUserRepository.save(sysUser);
    }

    /**
     * 删除请求
     * @return 删除状态
     */
    public void del(Long id) {
        sysUserRepository.deleteById(id);
    }

    /**
     * 修改密码
     * @return 修改密码状态
     */
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
}