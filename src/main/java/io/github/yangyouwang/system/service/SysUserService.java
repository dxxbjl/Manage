package io.github.yangyouwang.system.service;

import io.github.yangyouwang.system.dao.SysUserRepository;
import io.github.yangyouwang.system.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        // 通过用户名从数据库获取用户信息
        SysUser sysUser = sysUserRepository.findUserByLoginName(userName);
        if (sysUser == null) {
            throw new UsernameNotFoundException("账号不存在");
        }
        return sysUser;
    }
}