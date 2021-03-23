package io.github.yangyouwang.system.service;

import io.github.yangyouwang.system.dao.SysMenuRepository;
import io.github.yangyouwang.system.model.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
        if (userId == id) {
            menus = this.sysMenuRepository.findSysMenuByMenuTypeAndVisible();
        } else {
            menus = this.sysMenuRepository.findSysMenuByUserId(id);
        }
        return getChildPerms(menus, 0);
    }

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list 分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<SysMenu> getChildPerms(List<SysMenu> list, int parentId)
    {
        List<SysMenu> returnList = new ArrayList<>();
        for (Iterator<SysMenu> iterator = list.iterator(); iterator.hasNext();)
        {
            SysMenu t = iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == parentId)
            {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }


    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<SysMenu> list, SysMenu t)
    {
        // 得到子节点列表
        List<SysMenu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenu tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                // 判断是否有子节点
                Iterator<SysMenu> it = childList.iterator();
                while (it.hasNext())
                {
                    SysMenu n = it.next();
                    recursionFn(list, n);
                }
            }
        }
    }


    /**
     * 得到子节点列表
     */
    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t)
    {
        List<SysMenu> tlist = new ArrayList<>();
        Iterator<SysMenu> it = list.iterator();
        while (it.hasNext())
        {
            SysMenu n = it.next();
            if (n.getParentId().longValue() == t.getId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenu> list, SysMenu t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }
}
