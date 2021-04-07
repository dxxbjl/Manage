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
import org.thymeleaf.util.ArrayUtils;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
@Transactional
public class SysRoleService {

    @Autowired
    private SysRoleRepository sysRoleRepository;

    @Autowired
    private SysMenuRepository sysMenuRepository;

    /**
     * 跳转编辑
     * @return 编辑页面
     */
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
        // 查询菜单
        List<SysMenu> sysMenus = Arrays.stream(sysRoleAddReq.getMenuIds()).map(s -> {
            SysMenu sysMenu = sysMenuRepository.findById(s).orElse(new SysMenu());
            return sysMenu;
        }).collect(Collectors.toList());
        sysRole.setMenus(sysMenus);
        // 添加角色
        sysRoleRepository.save(sysRole);
    }

    /**
     * 编辑请求
     * @return 编辑状态
     */
    public void edit(SysRoleEditReq sysRoleEditReq) {
        SysRole sysRole = sysRoleRepository.findById(sysRoleEditReq.getId()).get();
        BeanUtils.copyProperties(sysRoleEditReq,sysRole);
        // 查询菜单
        List<SysMenu> sysMenus = Arrays.stream(sysRoleEditReq.getMenuIds()).map(s -> {
            SysMenu sysMenu = sysMenuRepository.findById(s).orElse(new SysMenu());
            return sysMenu;
        }).collect(Collectors.toList());
        sysRole.setMenus(sysMenus);
        sysRoleRepository.save(sysRole);
    }

    /**
     * 删除请求
     * @return 删除状态
     */
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
