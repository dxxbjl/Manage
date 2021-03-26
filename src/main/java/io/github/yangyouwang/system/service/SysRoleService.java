package io.github.yangyouwang.system.service;

import io.github.yangyouwang.system.dao.SysRoleRepository;
import io.github.yangyouwang.system.model.SysRole;
import io.github.yangyouwang.system.model.req.SysRoleAddReq;
import io.github.yangyouwang.system.model.req.SysRoleEditReq;
import io.github.yangyouwang.system.model.req.SysRoleListReq;
import io.github.yangyouwang.system.model.resp.SysRoleResp;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

/**
 * @author yangyouwang
 * @title: SysRoleService
 * @projectName crud
 * @description: 角色业务层
 * @date 2021/3/269:44 PM
 */
@Service
@Transactional
public class SysRoleService {

    @Autowired
    private SysRoleRepository sysRoleRepository;

    /**
     * 跳转编辑
     * @return 编辑页面
     */
    public SysRoleResp detail(Long id) {
        SysRole sysRole = sysRoleRepository.findById(id).orElse(new SysRole());
        SysRoleResp sysRoleResp = new SysRoleResp();
        BeanUtils.copyProperties(sysRole,sysRoleResp);
        return sysRoleResp;
    }

    /**
     * 列表请求
     * @return 请求列表
     */
    public Page<SysRoleResp> list(SysRoleListReq sysRoleListReq) {
        Pageable pageable = PageRequest.of(sysRoleListReq.getPageNum() - 1,sysRoleListReq.getPageSize());
        return sysRoleRepository.findPage(sysRoleListReq, pageable);
    }

    /**
     * 添加请求
     * @return 添加状态
     */
    public void add(SysRoleAddReq sysRoleAddReq) {
        SysRole sysRole = sysRoleRepository.findByRoleKey(sysRoleAddReq.getRoleKey());
        if (Objects.nonNull(sysRole)) {
            throw new RuntimeException("角色已存在");
        }
        sysRole = new SysRole();
        BeanUtils.copyProperties(sysRoleAddReq,sysRole);
        sysRoleRepository.save(sysRole);
    }

    /**
     * 编辑请求
     * @return 编辑状态
     */
    public void edit(SysRoleEditReq sysRoleEditReq) {
        SysRole sysRole = sysRoleRepository.findById(sysRoleEditReq.getId()).get();
        BeanUtils.copyProperties(sysRoleEditReq,sysRole);
        sysRoleRepository.save(sysRole);
    }

    /**
     * 删除请求
     * @return 删除状态
     */
    public void del(Long id) {
        sysRoleRepository.deleteById(id);
    }
}
