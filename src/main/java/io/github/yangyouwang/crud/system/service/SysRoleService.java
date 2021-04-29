package io.github.yangyouwang.crud.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.yangyouwang.common.domain.XmSelectNode;
import io.github.yangyouwang.crud.system.mapper.SysRoleMapper;
import io.github.yangyouwang.crud.system.mapper.SysRoleMenuMapper;
import io.github.yangyouwang.crud.system.model.SysMenu;
import io.github.yangyouwang.crud.system.model.SysRole;
import io.github.yangyouwang.crud.system.model.SysRoleMenu;
import io.github.yangyouwang.crud.system.model.req.SysRoleAddReq;
import io.github.yangyouwang.crud.system.model.req.SysRoleEditReq;
import io.github.yangyouwang.crud.system.model.req.SysRoleListReq;
import io.github.yangyouwang.crud.system.model.resp.SysRoleResp;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.thymeleaf.util.ArrayUtils;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.*;

/**
 * @author yangyouwang
 * @title: SysRoleService
 * @projectName crud
 * @description: 角色业务层
 * @date 2021/3/269:44 PM
 */
@Service
public class SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    /**
     * 跳转编辑
     * @return 编辑页面
     */
    @Transactional(readOnly = true)
    public SysRoleResp detail(Long id) {
        SysRole sysRole = sysRoleMapper.findRoleById(id);
        SysRoleResp sysRoleResp = new SysRoleResp();
        BeanUtils.copyProperties(sysRole,sysRoleResp);
        ofNullable(sysRole.getMenus()).ifPresent(menus -> {
            Long[] menuIds = menus.stream().map(SysMenu::getId).toArray(Long[]::new);
            sysRoleResp.setMenuIds(menuIds);
        });
        return sysRoleResp;
    }

    /**
     * 列表请求
     * @return 请求列表
     */
    @Transactional(readOnly = true)
    public IPage<SysRole> list(SysRoleListReq sysRoleListReq) {
        return sysRoleMapper.selectPage(new Page<>(sysRoleListReq.getPageNum(), sysRoleListReq.getPageSize()),
                new LambdaQueryWrapper<SysRole>()
                        .like(StringUtils.isNotBlank(sysRoleListReq.getRoleName()), SysRole::getRoleName , sysRoleListReq.getRoleName())
                        .like(StringUtils.isNotBlank(sysRoleListReq.getRoleKey()), SysRole::getRoleKey , sysRoleListReq.getRoleKey()));
    }

    /**
     * 添加请求
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public int add(@NotNull SysRoleAddReq sysRoleAddReq) {
        SysRole sysRole = sysRoleMapper.selectOne(new LambdaQueryWrapper<SysRole>().eq(SysRole::getRoleKey,sysRoleAddReq.getRoleKey()));
        Assert.isNull(sysRole, "角色已存在");
        // 添加角色
        SysRole role = new SysRole();
        BeanUtils.copyProperties(sysRoleAddReq,role);
        // 关联菜单
        if (sysRoleMapper.insert(role) > 0) {
            return insertRoleMenuBatch(role.getId(), sysRoleAddReq.getMenuIds());
        }
        throw new RuntimeException("添加角色失败");
    }

    /**
     * 编辑请求
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public int edit(SysRoleEditReq sysRoleEditReq) {
        SysRole role = new SysRole();
        BeanUtils.copyProperties(sysRoleEditReq,role);
        // 关联菜单
        if (sysRoleMapper.updateById(role) > 0) {
            if (sysRoleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>()
                    .eq(SysRoleMenu::getRoleId, role.getId())) > 0) {
                return insertRoleMenuBatch(role.getId(), sysRoleEditReq.getMenuIds());
            }
        }
        throw new RuntimeException("修改角色失败");
    }

    /**
     * 批量新增角色关联菜单
     * @param roleId 角色id
     * @param menuIds 菜单id
     * @return 操作状态
     */
    private int insertRoleMenuBatch(Long roleId, Long[] menuIds) {
        List<SysRoleMenu> roleMenus = Arrays.stream(menuIds).map(s -> {
            SysRoleMenu roleMenu = new SysRoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(s);
            return roleMenu;
        }).collect(Collectors.toList());
        return sysRoleMenuMapper.insertBatch(roleMenus);
    }

    /**
     * 删除请求
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public int del(Long id) {
        if (sysRoleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>()
                .eq(SysRoleMenu::getRoleId,id)) > 0) {
            return sysRoleMapper.deleteById(id);
        }
        throw new RuntimeException("删除角色失败");
    }

    /**
     * 查询角色列表
     * @param ids ids
     * @return 角色列表
     */
    @Transactional(readOnly = true)
    public List<XmSelectNode> xmSelect(Long[] ids) {
        List<SysRole> sysRoles = sysRoleMapper.selectList(new LambdaQueryWrapper<>());
        return sysRoles.stream().map(sysMenu -> {
            XmSelectNode treeNode = new XmSelectNode();
            treeNode.setName(sysMenu.getRoleName());
            treeNode.setValue(sysMenu.getId());
            treeNode.setId(sysMenu.getId());
            ofNullable(ids).ifPresent(optIds -> treeNode.setSelected(ArrayUtils.contains(optIds,sysMenu.getId())));
            return treeNode;
        }).collect(Collectors.toList());
    }
}
