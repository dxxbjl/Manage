package io.github.yangyouwang.crud.system.controller;

import io.github.yangyouwang.core.util.SecurityUtils;
import io.github.yangyouwang.crud.system.model.SysMenu;
import io.github.yangyouwang.crud.system.model.resp.SysUserResp;
import io.github.yangyouwang.crud.system.service.SysMenuService;
import io.github.yangyouwang.crud.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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

    private final SysMenuService sysMenuService;

    private final SysUserService sysUserService;

    @Autowired
    public SysLoginController(SysMenuService sysMenuService, SysUserService sysUserService) {
        this.sysMenuService = sysMenuService;
        this.sysUserService = sysUserService;
    }

    /**
     * 跳转到首页页面
     * @return 首页页面
     */
    @GetMapping("/")
    public String indexPage(ModelMap map) {
        User user = SecurityUtils.getSysUser();
        SysUserResp sysUser = sysUserService.findUserByName(user.getUsername());
        List<SysMenu> sysMenus = sysMenuService.selectMenusByUser(sysUser.getId());
        map.put("sysMenus",sysMenus);
        map.put("sysUser",sysUser);
        return "/index";
    }

    /**
     * 跳转到登陆页面
     * @return 登陆页面
     */
    @GetMapping("/loginPage")
    public String loginPage(){
        return "/login";
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
