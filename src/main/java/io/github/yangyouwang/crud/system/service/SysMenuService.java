package io.github.yangyouwang.crud.system.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.yangyouwang.common.constant.Constants;
import io.github.yangyouwang.common.domain.TreeSelectNode;
import io.github.yangyouwang.common.domain.XmSelectNode;
import io.github.yangyouwang.common.enums.ResultStatus;
import io.github.yangyouwang.core.converter.ListToTree;
import io.github.yangyouwang.core.converter.impl.ListToTreeImpl;
import io.github.yangyouwang.core.exception.CrudException;
import io.github.yangyouwang.crud.system.mapper.SysMenuMapper;
import io.github.yangyouwang.crud.system.mapper.SysRoleMenuMapper;
import io.github.yangyouwang.crud.system.entity.SysMenu;
import io.github.yangyouwang.crud.system.entity.SysRoleMenu;
import io.github.yangyouwang.crud.system.model.params.SysMenuAddDTO;
import io.github.yangyouwang.crud.system.model.params.SysMenuEditDTO;
import io.github.yangyouwang.crud.system.model.params.SysMenuListDTO;
import io.github.yangyouwang.crud.system.model.params.SysMenuVisibleDTO;
import io.github.yangyouwang.crud.system.model.result.SysMenuDTO;
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
    public List<SysMenuDTO> selectMenusByUser(Long userId) {
        List<SysMenu> menus;
        if (Constants.ADMIN_USER.equals(userId)) {
            menus = this.sysMenuMapper.findMenu();
        } else {
            menus = this.sysMenuMapper.findMenuByUserId(userId);
        }
        if (menus.size() == 0) {
            throw new AccessDeniedException(ResultStatus.MENU_NULL_ERROR.message);
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
     * 跳转编辑
     * @param id 菜单id
     * @return 编辑页面
     */
    @Transactional(readOnly = true)
    public SysMenuDTO detail(Long id) {
        SysMenu sysMenu = sysMenuMapper.findMenuById(id);
        SysMenuDTO sysMenuDTO = new SysMenuDTO();
        BeanUtils.copyProperties(sysMenu,sysMenuDTO);
        return sysMenuDTO;
    }

    /**
     * 列表请求
     * @param sysMenuListDTO 请求菜单列表对象
     * @return 请求列表
     */
    @Transactional(readOnly = true)
    public List<SysMenuDTO> list(SysMenuListDTO sysMenuListDTO) {
        List<SysMenu> sysMenus = this.list(new LambdaQueryWrapper<SysMenu>()
                .like(StringUtils.isNotBlank(sysMenuListDTO.getMenuName()), SysMenu::getMenuName , sysMenuListDTO.getMenuName()));
        return sysMenus.stream().map(s -> {
            SysMenuDTO sysMenuDTO = new SysMenuDTO();
            BeanUtils.copyProperties(s,sysMenuDTO);
            return sysMenuDTO;
        }).collect(Collectors.toList());
    }

    /**
     * 添加请求
     * @param sysMenuAddDTO 添加菜单对象
     * @return 添加状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public boolean add(SysMenuAddDTO sysMenuAddDTO) {
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(sysMenuAddDTO,sysMenu);
        return this.save(sysMenu);
    }

    /**
     * 编辑请求
     * @param sysMenuEditDTO 编辑菜单对象
     * @return 修改状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public boolean edit(SysMenuEditDTO sysMenuEditDTO) {
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(sysMenuEditDTO,sysMenu);
        return this.updateById(sysMenu);
    }

    /**
     * 删除请求
     * @param id 菜单id
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public void del(Long id) {
        // 删除子菜单
        int count = this.count(new LambdaQueryWrapper<SysMenu>()
                .eq(SysMenu::getParentId, id));
        if (count != 0) {
            throw new CrudException(ResultStatus.MENU_EXIST_ERROR);
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
     * @param ids 菜单ids
     * @return 菜单列表
     */
    @Transactional(readOnly = true)
    public List<XmSelectNode> xmSelect(Long[] ids) {
        List<SysMenu> menus = this.list(new LambdaQueryWrapper<SysMenu>()
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
     * @param sysMenuVisibleDTO 更新菜单对象
     * @return 更新状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public boolean changeMenu(SysMenuVisibleDTO sysMenuVisibleDTO) {
        Long id = sysMenuVisibleDTO.getId();
        String visible = sysMenuVisibleDTO.getVisible();
        SysMenu sysMenu = new SysMenu();
        sysMenu.setId(id);
        sysMenu.setVisible(visible);
        return this.updateById(sysMenu);
    }
}
