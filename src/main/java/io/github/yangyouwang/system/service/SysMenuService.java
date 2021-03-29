package io.github.yangyouwang.system.service;
import io.github.yangyouwang.common.domain.TreeNode;
import io.github.yangyouwang.core.converter.ListToTree;
import io.github.yangyouwang.core.converter.impl.ListToTreeImpl;
import io.github.yangyouwang.system.dao.SysMenuRepository;
import io.github.yangyouwang.system.model.SysMenu;
import io.github.yangyouwang.system.model.req.SysMenuAddReq;
import io.github.yangyouwang.system.model.req.SysMenuEditReq;
import io.github.yangyouwang.system.model.req.SysMenuListReq;
import io.github.yangyouwang.system.model.resp.SysMenuResp;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
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

    private static final Long userId = 1L;

    @Autowired
    private SysMenuRepository sysMenuRepository;


    /**
     * 根据用户查询菜单
     * @param id 用户id
     * @return 菜单信息
     */
    public List<SysMenu> selectMenusByUser(Long id) {
        List<SysMenu> menus;
        if (userId.equals(id)) {
            menus = this.sysMenuRepository.findAll();
        } else {
            menus = this.sysMenuRepository.findSysMenuByUserId(id);
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
     * @param id id
     * @return 菜单列表
     */
    public List<TreeNode> treeSelect(Long id) {
        List<SysMenu> menus;
        if (null == id) {
            menus = this.sysMenuRepository.findAll();
        } else {
            menus = this.sysMenuRepository.findSysMenuByParentId(id);
        }
        ListToTree treeBuilder = new ListToTreeImpl();
        return treeBuilder.toTree(menus.stream().map(sysMenu -> {
            TreeNode treeNode = new TreeNode();
            treeNode.setId(sysMenu.getId());
            treeNode.setParentId(sysMenu.getParentId());
            treeNode.setName(sysMenu.getMenuName());
            treeNode.setChecked(false);
            treeNode.setOpen(false);
            return treeNode;
        }).collect(Collectors.toList()));
    }
}
