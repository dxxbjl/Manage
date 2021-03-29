package io.github.yangyouwang.system.controller;

import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.common.domain.TreeNode;
import io.github.yangyouwang.system.model.req.*;
import io.github.yangyouwang.system.model.resp.SysMenuResp;
import io.github.yangyouwang.system.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author yangyouwang
 * @title: SysMenuController
 * @projectName crud
 * @description: 菜单管理
 * @date 2021/3/2610:22 PM
 */
@Controller
@RequestMapping("/sysMenu")
public class SysMenuController {

    private static final String SUFFIX = "/system/sysMenu";

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 跳转列表
     * @return 列表页面
     */
    @GetMapping("/listPage")
    public String listPage(){
        return SUFFIX + "/list";
    }

    /**
     * 跳转添加
     * @return 添加页面
     */
    @GetMapping("/addPage")
    public String addPage(){
        return SUFFIX + "/add";
    }

    /**
     * 跳转编辑
     * @return 编辑页面
     */
    @GetMapping("/editPage/{id}")
    public String editPage(@PathVariable Long id, ModelMap map){
        SysMenuResp sysMenu = sysMenuService.detail(id);
        map.put("sysMenu",sysMenu);
        return SUFFIX + "/edit";
    }


    /**
     * 列表请求
     * @return 请求列表
     */
    @GetMapping("/list")
    @ResponseBody
    public Result list(SysMenuListReq sysMenuListReq){
        return Result.success(sysMenuService.list(sysMenuListReq));
    }

    /**
     * 添加请求
     * @return 添加状态
     */
    @PostMapping("/add")
    @ResponseBody
    public Result add(@RequestBody @Valid SysMenuAddReq sysMenuAddReq, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
        }
        sysMenuService.add(sysMenuAddReq);
        return Result.success();
    }

    /**
     * 编辑请求
     * @return 编辑状态
     */
    @PostMapping("/edit")
    @ResponseBody
    public Result edit(@RequestBody @Valid SysMenuEditReq sysMenuEditReq, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
        }
        sysMenuService.edit(sysMenuEditReq);
        return Result.success();
    }

    /**
     * 删除请求
     * @return 删除状态
     */
    @DeleteMapping("/del/{id}")
    @ResponseBody
    public Result del(@PathVariable Long id){
        sysMenuService.del(id);
        return Result.success();
    }

    /**
     * 查询菜单列表
     * @param id id
     * @return 菜单列表
     */
    @GetMapping(value = {"/treeSelect","/treeSelect/{id}"})
    @ResponseBody
    public List<TreeNode> treeSelect(@PathVariable(value = "id",required = false) Long id) {
        List<TreeNode> sysMenus = sysMenuService.treeSelect(id);
        return sysMenus;
    }
}
