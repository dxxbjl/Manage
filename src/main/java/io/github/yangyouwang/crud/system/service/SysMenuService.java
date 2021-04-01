package io.github.yangyouwang.crud.system.service;
import io.github.yangyouwang.common.constant.Constants;
import io.github.yangyouwang.common.domain.TreeSelectNode;
import io.github.yangyouwang.common.domain.XmSelectNode;
import io.github.yangyouwang.core.converter.ListToTree;
import io.github.yangyouwang.core.converter.impl.ListToTreeImpl;
import io.github.yangyouwang.crud.system.dao.SysMenuRepository;
import io.github.yangyouwang.crud.system.model.SysMenu;
import io.github.yangyouwang.crud.system.model.req.SysMenuAddReq;
import io.github.yangyouwang.crud.system.model.req.SysMenuEditReq;
import io.github.yangyouwang.crud.system.model.req.SysMenuListReq;
import io.github.yangyouwang.crud.system.model.resp.SysMenuResp;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ArrayUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author yangyouwang
 * @title: SysMenuService
 * @projectName crud
 * @description: 菜单业务层
 * @date 2021/3/2312:32 PM
 */
@Service
public class SysMenuService {

    @Autowired
    private SysMenuRepository sysMenuRepository;

    /**
     * 根据用户查询菜单
     * @param id 用户id
     * @return 菜单信息
     */
    public List<SysMenu> selectMenusByUser(Long id) {
        List<SysMenu> menus;
        if (Constants.ADMINISTRATOR_USER_ID.equals(id)) {
            menus = this.sysMenuRepository.findSysMenu();
        } else {
            menus = this.sysMenuRepository.findSysMenuByUserId(id);
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
    public SysMenuResp detail(Long id) {
        SysMenu sysMenu = sysMenuRepository.findSysMenuById(id);
        SysMenuResp sysMenuResp = new SysMenuResp();
        BeanUtils.copyProperties(sysMenu,sysMenuResp);
        return sysMenuResp;
    }

    /**
     * 列表请求
     * @return 请求列表
     */
    public List<SysMenuResp> list(SysMenuListReq sysMenuListReq) {
        Specification<SysMenu> query = (Specification<SysMenu>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(Strings.isNotBlank(sysMenuListReq.getMenuName())){
                predicates.add(criteriaBuilder.like(root.get("menuName"),"%" +sysMenuListReq.getMenuName() + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        List<SysMenu> sysMenus = sysMenuRepository.findAll(query);
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
    public void add(SysMenuAddReq sysMenuAddReq) {
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(sysMenuAddReq,sysMenu);
        sysMenuRepository.save(sysMenu);
    }

    /**
     * 编辑请求
     * @return 编辑状态
     */
    public void edit(SysMenuEditReq sysMenuEditReq) {
        SysMenu sysUser = sysMenuRepository.findById(sysMenuEditReq.getId()).get();
        BeanUtils.copyProperties(sysMenuEditReq,sysUser);
        sysMenuRepository.save(sysUser);
    }

    /**
     * 删除请求
     * @return 删除状态
     */
    public void del(Long id) {
        sysMenuRepository.deleteById(id);
    }

    /**
     * 查询菜单列表
     * @return 菜单列表
     */
    public List<TreeSelectNode> treeSelect() {
        List<SysMenu> menus = this.sysMenuRepository.findAll();
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
    public List<XmSelectNode> xmSelect(Long[] ids) {
        List<SysMenu> menus = this.sysMenuRepository.findAll();
        List<XmSelectNode> result = menus.stream().map(sysMenu -> {
            XmSelectNode treeNode = new XmSelectNode();
            treeNode.setName(sysMenu.getMenuName());
            treeNode.setValue(sysMenu.getId());
            treeNode.setId(sysMenu.getId());
            treeNode.setParentId(sysMenu.getParentId());
            Optional.ofNullable(ids).ifPresent(optIds -> treeNode.setSelected(ArrayUtils.contains(optIds,sysMenu.getId())));
            return treeNode;
        }).collect(Collectors.toList());
        ListToTree treeBuilder = new ListToTreeImpl();
        return treeBuilder.toTree(result);
    }
}
