package io.github.yangyouwang.system.service;

import io.github.yangyouwang.system.dao.SysUserRepository;
import io.github.yangyouwang.system.model.SysUser;
import io.github.yangyouwang.system.model.req.SysUserAddReq;
import io.github.yangyouwang.system.model.req.SysUserEditReq;
import io.github.yangyouwang.system.model.resp.SysUserResp;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Objects;

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
        BeanUtils.copyProperties(sysUser,sysUserResp);
        return sysUserResp;
    }

    /**
     * 列表请求
     * @return 列表
     */
    public Page<SysUserResp> list(@Nullable String userName, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1,pageSize);
        Page<SysUserResp> page = sysUserRepository.findPage(userName, pageable);
        return page;
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
        String passWord = passwordEncoder.encode(sysUserAddReq.getPassWord());
        sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserAddReq,sysUser);
        sysUser.setPassWord(passWord);
        sysUserRepository.save(sysUser);
    }

    /**
     * 编辑请求
     * @return 编辑状态
     */
    public void edit(SysUserEditReq sysUserEditReq) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserEditReq,sysUser);
        sysUserRepository.save(sysUser);
    }

    /**
     * 删除请求
     * @return 删除状态
     */
    public void del(Long id) {
        sysUserRepository.deleteById(id);
    }
}