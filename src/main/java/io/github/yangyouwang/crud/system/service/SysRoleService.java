package io.github.yangyouwang.crud.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.yangyouwang.common.domain.XmSelectNode;
import io.github.yangyouwang.common.enums.ResultStatus;
import io.github.yangyouwang.crud.system.mapper.SysRoleMapper;
import io.github.yangyouwang.crud.system.mapper.SysRoleMenuMapper;
import io.github.yangyouwang.crud.system.entity.SysMenu;
import io.github.yangyouwang.crud.system.entity.SysRole;
import io.github.yangyouwang.crud.system.entity.SysRoleMenu;
import io.github.yangyouwang.crud.system.model.params.SysRoleAddDTO;
import io.github.yangyouwang.crud.system.model.params.SysRoleEditDTO;
import io.github.yangyouwang.crud.system.model.params.SysRoleListDTO;
import io.github.yangyouwang.crud.system.model.result.SysRoleDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopContext;
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
public class SysRoleService extends ServiceImpl<SysRoleMapper,SysRole> {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    /**
     * 跳转编辑
     * @param id 角色id
     * @return 编辑页面
     */
    @Transactional(readOnly = true)
    public SysRoleDTO detail(Long id) {
        SysRole sysRole = sysRoleMapper.findRoleById(id);
        SysRoleDTO sysRoleDTO = new SysRoleDTO();
        BeanUtils.copyProperties(sysRole,sysRoleDTO);
        ofNullable(sysRole.getMenus()).ifPresent(menus -> {
            Long[] menuIds = menus.stream().map(SysMenu::getId).toArray(Long[]::new);
            sysRoleDTO.setMenuIds(menuIds);
        });
        return sysRoleDTO;
    }

    /**
     * 列表请求
     * @param sysRoleListDTO 请求角色列表对象
     * @return 请求列表
     */
    @Transactional(readOnly = true)
    public IPage list(SysRoleListDTO sysRoleListDTO) {
        return this.page(new Page<>(sysRoleListDTO.getPageNum(), sysRoleListDTO.getPageSize()),
                new LambdaQueryWrapper<SysRole>()
                        .like(StringUtils.isNotBlank(sysRoleListDTO.getRoleName()), SysRole::getRoleName , sysRoleListDTO.getRoleName())
                        .like(StringUtils.isNotBlank(sysRoleListDTO.getRoleKey()), SysRole::getRoleKey , sysRoleListDTO.getRoleKey()));
    }

    /**
     * 添加请求
     * @param sysRoleAddDTO 添加角色对象
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public void add(@NotNull SysRoleAddDTO sysRoleAddDTO) {
        SysRole sysRole = this.getOne(new LambdaQueryWrapper<SysRole>().eq(SysRole::getRoleKey,sysRoleAddDTO.getRoleKey()));
        Assert.isNull(sysRole, ResultStatus.ROLE_EXIST_ERROR.message);
        // 添加角色
        SysRole role = new SysRole();
        BeanUtils.copyProperties(sysRoleAddDTO,role);
        boolean flag = this.save(role);
        if (flag) {
            // 关联菜单
            SysRoleService proxy = (SysRoleService) AopContext.currentProxy();
            proxy.insertRoleMenuBatch(role.getId(), sysRoleAddDTO.getMenuIds());
        }
    }

    /**
     * 编辑请求
     * @param sysRoleEditDTO 编辑角色对象
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public void edit(SysRoleEditDTO sysRoleEditDTO) {
        SysRole role = new SysRole();
        BeanUtils.copyProperties(sysRoleEditDTO,role);
        boolean flag = this.updateById(role);
        // 关联菜单
        if (flag && sysRoleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>()
                .eq(SysRoleMenu::getRoleId, role.getId())) > 0) {
            SysRoleService proxy = (SysRoleService) AopContext.currentProxy();
            proxy.insertRoleMenuBatch(role.getId(), sysRoleEditDTO.getMenuIds());
        }
    }

    /**
     * 批量新增角色关联菜单
     * @param roleId 角色id
     * @param menuIds 菜单id
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public void insertRoleMenuBatch(Long roleId, Long[] menuIds) {
        List<SysRoleMenu> roleMenus = Arrays.stream(menuIds).map(s -> {
            SysRoleMenu roleMenu = new SysRoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(s);
            return roleMenu;
        }).collect(Collectors.toList());
        sysRoleMenuMapper.insertBatchSomeColumn(roleMenus);
    }

    /**
     * 删除请求
     * @param id 角色id
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public void del(Long id) {
        this.removeById(id);
        // 删除角色关联菜单
        sysRoleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>()
                .eq(SysRoleMenu::getRoleId,id));
    }

    /**
     * 查询角色列表
     * @param ids 角色ids
     * @return 角色列表
     */
    @Transactional(readOnly = true)
    public List<XmSelectNode> xmSelect(Long[] ids) {
        List<SysRole> sysRoles = this.list(new LambdaQueryWrapper<>());
        return sysRoles.stream().map(sysRole -> {
            XmSelectNode treeNode = new XmSelectNode();
            treeNode.setName(sysRole.getRoleName());
            treeNode.setValue(sysRole.getId());
            treeNode.setId(sysRole.getId());
            ofNullable(ids).ifPresent(optIds -> treeNode.setSelected(ArrayUtils.contains(optIds,sysRole.getId())));
            return treeNode;
        }).collect(Collectors.toList());
    }
}
