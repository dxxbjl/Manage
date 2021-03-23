package io.github.yangyouwang.system.controller;

import io.github.yangyouwang.system.model.SysMenu;
import io.github.yangyouwang.system.model.SysUser;
import io.github.yangyouwang.system.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author yangyouwang
 * @title: SysLoginController
 * @projectName crud
 * @description: 首页控制层
 * @date 2021/3/216:40 PM
 */
@Controller
public class SysLoginController {

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 跳转到登陆页面
     * @return 登陆页面
     */
    @GetMapping("/loginPage")
    public String loginPage(){
        return "/login";
    }

    /**
     * 跳转到首页页面
     * @return 首页页面
     */
    @GetMapping("/indexPage")
    public String indexPage(ModelMap map) {
        SysUser sysUser = (SysUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Assert.notNull(sysUser,"该用户未登陆");
        List<SysMenu> sysMenus = sysMenuService.selectMenusByUser(sysUser.getId());
        map.put("sysMenus",sysMenus);
        map.put("sysUser",sysUser);
        return "/index";
    }

    /**
     * 跳转到main页面
     * @return main页面
     */
    @GetMapping("/mainPage")
    public String mainPage(){
        return "/main";
    }
}
