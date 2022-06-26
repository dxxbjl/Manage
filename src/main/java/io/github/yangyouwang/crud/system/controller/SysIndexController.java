package io.github.yangyouwang.crud.system.controller;

import io.github.yangyouwang.common.constant.ConfigConsts;
import io.github.yangyouwang.core.util.SecurityUtils;
import io.github.yangyouwang.core.util.StringUtil;
import io.github.yangyouwang.crud.system.model.result.SysMenuDTO;
import io.github.yangyouwang.crud.system.model.result.SysUserDTO;
import io.github.yangyouwang.crud.system.service.SysDictTypeService;
import io.github.yangyouwang.crud.system.service.SysMenuService;
import io.github.yangyouwang.crud.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

/**
 * @author yangyouwang
 * @title: SysLoginController
 * @projectName crud
 * @description: 首页控制层
 * @date 2021/3/216:40 PM
 */
@Controller
public class SysIndexController {

    private final SysMenuService sysMenuService;

    private final SysUserService sysUserService;

    private final SysDictTypeService sysDictTypeService;

    @Autowired
    public SysIndexController(SysMenuService sysMenuService, SysUserService sysUserService, SysDictTypeService sysDictTypeService) {
        this.sysMenuService = sysMenuService;
        this.sysUserService = sysUserService;
        this.sysDictTypeService = sysDictTypeService;
    }

    /**
     * 跳转到首页页面
     * @return 首页页面
     */
    @GetMapping("/")
    public String indexPage(ModelMap map) {
        // 缓存字典
        sysDictTypeService.cacheDict();
        // 用户信息
        User user = SecurityUtils.getSysUser();
        SysUserDTO sysUser = sysUserService.findUserByName(user.getUsername());
        map.put("sysUser",sysUser);
        // 菜单权限
        List<SysMenuDTO> sysMenus = sysMenuService.selectMenusByUser(sysUser.getId());
        map.put("sysMenus",sysMenus);
        return "index";
    }

    /**
     * 跳转到登陆页面
     * @return 登陆页面
     */
    @GetMapping("/loginPage")
    public String loginPage(){
        return "login";
    }

    /**
     * 跳转到main页面
     * @return main页面
     */
    @GetMapping("/mainPage")
    public String mainPage(){
        return "main";
    }

    /**
     * 验证码
     */
    @RequestMapping("/getImgCode")
    public void getImgCode(HttpServletRequest request, HttpServletResponse response) {
        int width = 80;
        int height = 30;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //获取画笔
        Graphics graphics = image.getGraphics();
        //设置画笔颜色为白色
        graphics.setColor(Color.white);
        //填充图片
        graphics.fillRect(0, 0, width, height);
        //设置画笔颜色为黑色
        graphics.setColor(Color.black);
        //设置字体的小大
        graphics.setFont(new Font("黑体", Font.BOLD, 24));
        //产生4个随机验证码
        String checkCode = StringUtil.getCheckCode();
        //将验证码放入HttpSession中
        HttpSession session = request.getSession();
        session.setAttribute(ConfigConsts.IMAGE_CODE_SESSION, checkCode);
        //向图片上写入验证码
        graphics.drawString(checkCode, 15, 25);
        //将内存中的图片输出到浏览器
        try {
            response.setContentType("image/png");
            ImageIO.write(image, "PNG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
