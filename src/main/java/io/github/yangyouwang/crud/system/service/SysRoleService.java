package io.github.yangyouwang.crud.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.yangyouwang.common.domain.XmSelectNode;
import io.github.yangyouwang.common.enums.ResultStatus;
import io.github.yangyouwang.core.util.StringUtil;
import io.github.yangyouwang.crud.system.mapper.SysRoleMapper;
import io.github.yangyouwang.crud.system.mapper.SysRoleMenuMapper;
import io.github.yangyouwang.crud.system.entity.SysRole;
import io.github.yangyouwang.crud.system.entity.SysRoleMenu;
import org.springframework.aop.framework.AopContext;
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
    public SysRole detail(Long id) {
        return sysRoleMapper.findRoleById(id);
    }

    /**
     * 添加请求
     * @param sysRole 添加角色对象
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public void add(@NotNull SysRole sysRole) {
        SysRole old = this.getOne(new LambdaQueryWrapper<SysRole>().eq(SysRole::getRoleKey,sysRole.getRoleKey()));
        Assert.isNull(old, ResultStatus.ROLE_EXIST_ERROR.message);
        // 添加角色
        boolean flag = this.save(sysRole);
        if (flag) {
            // 关联菜单
            SysRoleService proxy = (SysRoleService) AopContext.currentProxy();
            proxy.insertRoleMenuBatch(sysRole.getId(), StringUtil.getId(sysRole.getMenuIds()));
        }
    }

    /**
     * 编辑请求
     * @param sysRole 编辑角色对象
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public void edit(SysRole sysRole) {
        boolean flag = this.updateById(sysRole);
        if (flag) {
            // 删除角色菜单
            sysRoleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, sysRole.getId()));
            SysRoleService proxy = (SysRoleService) AopContext.currentProxy();
            proxy.insertRoleMenuBatch(sysRole.getId(), StringUtil.getId(sysRole.getMenuIds()));
        }
    }

    /**
     * 批量新增角色关联菜单
     * @param roleId 角色id
     * @param menuIds 菜单id
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public void insertRoleMenuBatch(Long roleId, Long[] menuIds) {
        if (menuIds.length > 0) {
            List<SysRoleMenu> roleMenus = Arrays.stream(menuIds).map(s -> {
                SysRoleMenu roleMenu = new SysRoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(s);
                return roleMenu;
            }).collect(Collectors.toList());
            sysRoleMenuMapper.insertBatchSomeColumn(roleMenus);
        }
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
    public List<XmSelectNode> xmSelect(String ids) {
        List<SysRole> sysRoles = this.list(new LambdaQueryWrapper<>());
        return sysRoles.stream().map(sysRole -> {
            XmSelectNode treeNode = new XmSelectNode();
            treeNode.setName(sysRole.getRoleName());
            treeNode.setValue(sysRole.getId());
            treeNode.setId(sysRole.getId());
            ofNullable(ids).ifPresent(optIds -> treeNode.setSelected(ArrayUtils.contains(StringUtil.getId(optIds),sysRole.getId())));
            return treeNode;
        }).collect(Collectors.toList());
    }
}
