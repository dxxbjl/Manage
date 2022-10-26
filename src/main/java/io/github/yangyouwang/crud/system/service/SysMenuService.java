package io.github.yangyouwang.crud.system.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.yangyouwang.common.constant.ConfigConsts;
import io.github.yangyouwang.common.domain.TreeSelectNode;
import io.github.yangyouwang.common.enums.ResultStatus;
import io.github.yangyouwang.core.converter.ListToTree;
import io.github.yangyouwang.core.converter.impl.ListToTreeImpl;
import io.github.yangyouwang.core.exception.CrudException;
import io.github.yangyouwang.core.util.StringUtil;
import io.github.yangyouwang.crud.system.mapper.SysMenuMapper;
import io.github.yangyouwang.crud.system.mapper.SysRoleMenuMapper;
import io.github.yangyouwang.crud.system.entity.SysMenu;
import io.github.yangyouwang.crud.system.entity.SysRoleMenu;
import io.github.yangyouwang.crud.system.model.SysMenuDTO;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.ArrayUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;


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
     * @param userId 用户ID
     * @param userName 用户名称
     * @return 菜单信息
     */
    @Transactional(readOnly = true)
    public List<SysMenuDTO> selectMenusByUser(Long userId,String userName) {
        List<SysMenu> menus;
        if (ConfigConsts.ADMIN_USER.equals(userName)) {
            menus = sysMenuMapper.findMenu();
        } else {
            menus = sysMenuMapper.findMenuByUserId(userId);
        }
        if (menus.isEmpty()) {
            throw new AccessDeniedException("暂未分配菜单");
        }
        List<SysMenuDTO> sysMenuDTOS = menus.stream().map(sysMenu -> {
            SysMenuDTO sysMenuDTO = new SysMenuDTO();
            BeanUtil.copyProperties(sysMenu, sysMenuDTO, true, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
            return sysMenuDTO;
        }).collect(Collectors.toList());
        ListToTree treeBuilder = new ListToTreeImpl();
        return treeBuilder.toTree(sysMenuDTOS);
    }

    /**
     * 删除请求
     * @param id 菜单id
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public void remove(Long id) {
        // 删除子菜单
        int count = this.count(new LambdaQueryWrapper<SysMenu>()
                .eq(SysMenu::getParentId, id));
        if (count != 0) {
            throw new CrudException(ResultStatus.DATA_EXIST_ERROR);
        }
        this.removeById(id);
        // 删除角色关联菜单
        sysRoleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>()
                .eq(SysRoleMenu::getMenuId, id));
    }
    /**
     * 查询菜单列表
     * @return 菜单列表
     */
    @Transactional(readOnly = true)
    public List<TreeSelectNode> treeSelect() {
        List<SysMenu> menus = this.list(new LambdaQueryWrapper<SysMenu>()
                .eq(SysMenu::getVisible,ConfigConsts.ENABLED_YES));
        if (menus.isEmpty()) {
            return Collections.emptyList();
        }
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
     * 根据菜单ids查询选中菜单树结构
     * @param ids 菜单ids
     * @return 菜单树结构
     */
    @Transactional(readOnly = true)
    public List<TreeSelectNode> xmSelect(String ids) {
        List<SysMenu> menus = this.list(new LambdaQueryWrapper<SysMenu>()
                .eq(SysMenu::getVisible,ConfigConsts.ENABLED_YES));
        // 父菜单ID是否存在顶级
        boolean flag = menus.stream().anyMatch(s -> 0 == s.getParentId());
        if (menus.isEmpty()|| !flag) {
            return Collections.emptyList();
        }
        List<TreeSelectNode> result = menus.stream().map(sysMenu -> {
            TreeSelectNode treeNode = new TreeSelectNode();
            treeNode.setName(sysMenu.getMenuName());
            treeNode.setValue(sysMenu.getId());
            treeNode.setId(sysMenu.getId());
            treeNode.setParentId(sysMenu.getParentId());
            ofNullable(ids).ifPresent(optIds -> treeNode.setSelected(ArrayUtils.contains(StringUtil.getId(optIds),sysMenu.getId())));
            return treeNode;
        }).collect(Collectors.toList());
        ListToTree treeBuilder = new ListToTreeImpl();
        return treeBuilder.toTree(result);
    }
}
