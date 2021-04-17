package io.github.yangyouwang.crud.system.service;

import io.github.yangyouwang.common.domain.XmSelectNode;
import io.github.yangyouwang.crud.system.dao.SysMenuRepository;
import io.github.yangyouwang.crud.system.dao.SysRoleRepository;
import io.github.yangyouwang.crud.system.model.SysMenu;
import io.github.yangyouwang.crud.system.model.SysRole;
import io.github.yangyouwang.crud.system.model.req.SysRoleAddReq;
import io.github.yangyouwang.crud.system.model.req.SysRoleEditReq;
import io.github.yangyouwang.crud.system.model.req.SysRoleListReq;
import io.github.yangyouwang.crud.system.model.resp.SysRoleResp;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.thymeleaf.util.ArrayUtils;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author yangyouwang
 * @title: SysRoleService
 * @projectName crud
 * @description: 角色业务层
 * @date 2021/3/269:44 PM
 */
@Service
public class SysRoleService {

    @Autowired
    private SysRoleRepository sysRoleRepository;

    @Autowired
    private SysMenuRepository sysMenuRepository;

    /**
     * 跳转编辑
     * @return 编辑页面
     */
    @Transactional(readOnly = true)
    public SysRoleResp detail(Long id) {
        SysRole sysRole = sysRoleRepository.findById(id).orElse(new SysRole());
        SysRoleResp sysRoleResp = new SysRoleResp();
        BeanUtils.copyProperties(sysRole,sysRoleResp);
        Long[] menuIds = sysRole.getMenus().stream().map(s -> s.getId()).toArray(Long[]::new);
        sysRoleResp.setMenuIds(menuIds);
        return sysRoleResp;
    }

    /**
     * 列表请求
     * @return 请求列表
     */
    @Transactional(readOnly = true)
    public Page<SysRoleResp> list(SysRoleListReq sysRoleListReq) {
        Pageable pageable = PageRequest.of(sysRoleListReq.getPageNum() - 1,sysRoleListReq.getPageSize());
        return sysRoleRepository.findPage(sysRoleListReq, pageable);
    }

    /**
     * 添加请求
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    public void add(@NotNull SysRoleAddReq sysRoleAddReq) {
        SysRole sysRole = sysRoleRepository.findByRoleKey(sysRoleAddReq.getRoleKey());
        Assert.isNull(sysRole, "角色已存在");
        // 查询菜单
        List<SysMenu> sysMenus = Arrays.stream(sysRoleAddReq.getMenuIds()).map(s ->
                sysMenuRepository.findById(s).orElse(new SysMenu())).collect(Collectors.toList());
        // 添加角色
        sysRole = new SysRole();
        BeanUtils.copyProperties(sysRoleAddReq,sysRole);
        sysRole.setMenus(sysMenus);
        sysRoleRepository.save(sysRole);
    }

    /**
     * 编辑请求
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    public void edit(SysRoleEditReq sysRoleEditReq) {
        sysRoleRepository.findById(sysRoleEditReq.getId()).ifPresent(sysRole -> {
            BeanUtils.copyProperties(sysRoleEditReq,sysRole);
            // 查询菜单
            List<SysMenu> sysMenus = Arrays.stream(sysRoleEditReq.getMenuIds()).map(s ->
                    sysMenuRepository.findById(s).orElse(new SysMenu())).collect(Collectors.toList());
            sysRole.setMenus(sysMenus);
            sysRoleRepository.save(sysRole);
        });
    }

    /**
     * 删除请求
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    public void del(Long id) {
        if(sysRoleRepository.existsById(id)) {
            sysRoleRepository.deleteById(id);
        }
    }

    /**
     * 查询角色列表
     * @param ids ids
     * @return 角色列表
     */
    @Transactional(readOnly = true)
    public List<XmSelectNode> xmSelect(Long[] ids) {
        List<SysRole> sysRoles = this.sysRoleRepository.findAll();
        return sysRoles.stream().map(sysMenu -> {
            XmSelectNode treeNode = new XmSelectNode();
            treeNode.setName(sysMenu.getRoleName());
            treeNode.setValue(sysMenu.getId());
            treeNode.setId(sysMenu.getId());
            Optional.ofNullable(ids).ifPresent(optIds -> treeNode.setSelected(ArrayUtils.contains(optIds,sysMenu.getId())));
            return treeNode;
        }).collect(Collectors.toList());
    }
}
