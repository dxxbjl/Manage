package io.github.yangyouwang.crud.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.yangyouwang.common.constant.Constants;
import io.github.yangyouwang.common.domain.TreeSelectNode;
import io.github.yangyouwang.common.domain.XmSelectNode;
import io.github.yangyouwang.core.converter.ListToTree;
import io.github.yangyouwang.core.converter.impl.ListToTreeImpl;
import io.github.yangyouwang.crud.system.mapper.SysMenuMapper;
import io.github.yangyouwang.crud.system.mapper.SysRoleMenuMapper;
import io.github.yangyouwang.crud.system.model.SysMenu;
import io.github.yangyouwang.crud.system.model.SysRoleMenu;
import io.github.yangyouwang.crud.system.model.req.SysMenuAddReq;
import io.github.yangyouwang.crud.system.model.req.SysMenuEditReq;
import io.github.yangyouwang.crud.system.model.req.SysMenuListReq;
import io.github.yangyouwang.crud.system.model.req.SysMenuVisibleReq;
import io.github.yangyouwang.crud.system.model.resp.SysMenuResp;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.ArrayUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.*;

/**
 * @author yangyouwang
 * @title: SysMenuService
 * @projectName crud
 * @description: 菜单业务层
 * @date 2021/3/2312:32 PM
 */
@Service
public class SysMenuService extends ServiceImpl<SysMenuMapper, SysMenu> {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    /**
     * 根据用户查询菜单
     * @param userId 用户id
     * @return 菜单信息
     */
    @Transactional(readOnly = true)
    public List<SysMenu> selectMenusByUser(Long userId) {
        List<SysMenu> menus;
        if (Constants.ADMIN_USER.equals(userId)) {
            menus = this.sysMenuMapper.findMenu();
        } else {
            menus = this.sysMenuMapper.findMenuByUserId(userId);
        }
        if (menus.size() == 0) {
            throw new AccessDeniedException("暂未分配菜单");
        }
        ListToTree treeBuilder = new ListToTreeImpl();
        return treeBuilder.toTree(menus);
    }

    /**
     * 跳转编辑
     * @return 编辑页面
     */
    @Transactional(readOnly = true)
    public SysMenuResp detail(Long id) {
        SysMenu sysMenu = sysMenuMapper.findMenuById(id);
        SysMenuResp sysMenuResp = new SysMenuResp();
        BeanUtils.copyProperties(sysMenu,sysMenuResp);
        return sysMenuResp;
    }

    /**
     * 列表请求
     * @return 请求列表
     */
    @Transactional(readOnly = true)
    public List<SysMenuResp> list(SysMenuListReq sysMenuListReq) {
        List<SysMenu> sysMenus = sysMenuMapper.selectList(new LambdaQueryWrapper<SysMenu>()
                .like(StringUtils.isNotBlank(sysMenuListReq.getMenuName()), SysMenu::getMenuName , sysMenuListReq.getMenuName()));
        return sysMenus.stream().map(s -> {
            SysMenuResp sysMenuResp = new SysMenuResp();
            BeanUtils.copyProperties(s,sysMenuResp);
            return sysMenuResp;
        }).collect(Collectors.toList());
    }

    /**
     * 添加请求
     * @return 添加状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public int add(SysMenuAddReq sysMenuAddReq) {
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(sysMenuAddReq,sysMenu);
        return sysMenuMapper.insert(sysMenu);
    }

    /**
     * 编辑请求
     * @return 修改状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public int edit(SysMenuEditReq sysMenuEditReq) {
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(sysMenuEditReq,sysMenu);
        return sysMenuMapper.updateById(sysMenu);
    }

    /**
     * 删除请求
     * @return 删除状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public int del(Long id) {
        int flag = sysMenuMapper.deleteById(id);
        if (flag > 0) {
            // 删除子菜单
            sysMenuMapper.delete(new LambdaQueryWrapper<SysMenu>()
                    .eq(SysMenu::getParentId, id));
            // 删除角色关联菜单
            sysRoleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>()
                    .eq(SysRoleMenu::getMenuId, id));
            return flag;
        }
        throw new RuntimeException("删除菜单失败");
    }

    /**
     * 查询菜单列表
     * @return 菜单列表
     */
    @Transactional(readOnly = true)
    public List<TreeSelectNode> treeSelect() {
        List<SysMenu> menus = sysMenuMapper.selectList(new LambdaQueryWrapper<SysMenu>()
                .eq(SysMenu::getVisible,Constants.ENABLED_YES));
        List<TreeSelectNode> result = menus.stream().map(sysMenu -> {
            TreeSelectNode treeNode = new TreeSelectNode();
            treeNode.setId(sysMenu.getId());
            treeNode.setParentId(sysMenu.getParentId());
            treeNode.setName(sysMenu.getMenuName());
            return treeNode;
        }).collect(Collectors.toList());
        ListToTree treeBuilder = new ListToTreeImpl();
        return treeBuilder.toTree(result);
    }


    /**
     * 查询菜单列表
     * @param ids ids
     * @return 菜单列表
     */
    @Transactional(readOnly = true)
    public List<XmSelectNode> xmSelect(Long[] ids) {
        List<SysMenu> menus = sysMenuMapper.selectList(new LambdaQueryWrapper<SysMenu>()
                .eq(SysMenu::getVisible,Constants.ENABLED_YES));
        List<XmSelectNode> result = menus.stream().map(sysMenu -> {
            XmSelectNode treeNode = new XmSelectNode();
            treeNode.setName(sysMenu.getMenuName());
            treeNode.setValue(sysMenu.getId());
            treeNode.setId(sysMenu.getId());
            treeNode.setParentId(sysMenu.getParentId());
            ofNullable(ids).ifPresent(optIds -> treeNode.setSelected(ArrayUtils.contains(optIds,sysMenu.getId())));
            return treeNode;
        }).collect(Collectors.toList());
        ListToTree treeBuilder = new ListToTreeImpl();
        return treeBuilder.toTree(result);
    }

    /**
     * 更新菜单状态
     * @return 更新状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public int changeMenu(SysMenuVisibleReq sysMenuVisibleReq) {
        SysMenu sysMenu = new SysMenu();
        sysMenu.setId(sysMenuVisibleReq.getId());
        sysMenu.setVisible(sysMenuVisibleReq.getVisible());
        return sysMenuMapper.updateById(sysMenu);
    }
}
