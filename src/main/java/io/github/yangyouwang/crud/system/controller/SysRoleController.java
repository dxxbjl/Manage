package io.github.yangyouwang.crud.system.controller;

import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.common.domain.XmSelectNode;
import io.github.yangyouwang.crud.system.model.req.*;
import io.github.yangyouwang.crud.system.model.resp.SysRoleResp;
import io.github.yangyouwang.crud.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author yangyouwang
 * @title: SysRoleController
 * @projectName crud
 * @description: 角色控制层
 * @date 2021/3/2112:38 AM
 */
@Controller
@RequestMapping("/sysRole")
public class SysRoleController {

    private static final String SUFFIX = "/system/sysRole";

    @Autowired
    private SysRoleService sysRoleService;

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
    public String editPage(@Valid @NotNull(message = "id不能为空") @PathVariable Long id, ModelMap map){
        SysRoleResp sysRole = sysRoleService.detail(id);
        map.put("sysRole",sysRole);
        return SUFFIX + "/edit";
    }


    /**
     * 列表请求
     * @return 请求列表
     */
    @GetMapping("/list")
    @ResponseBody
    public Result list(@Validated SysRoleListReq sysRoleListReq, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
        }
        return Result.success(sysRoleService.list(sysRoleListReq));
    }

    /**
     * 添加请求
     * @return 添加状态
     */
    @PostMapping("/add")
    @ResponseBody
    public Result add(@RequestBody @Validated SysRoleAddReq sysRoleAddReq, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
        }
        sysRoleService.add(sysRoleAddReq);
        return Result.success();
    }

    /**
     * 编辑请求
     * @return 编辑状态
     */
    @PostMapping("/edit")
    @ResponseBody
    public Result edit(@RequestBody @Validated SysRoleEditReq sysRoleEditReq, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
        }
        sysRoleService.edit(sysRoleEditReq);
        return Result.success();
    }

    /**
     * 删除请求
     * @return 删除状态
     */
    @DeleteMapping("/del/{id}")
    @ResponseBody
    public Result del(@Valid @NotNull(message = "id不能为空") @PathVariable Long id){
        sysRoleService.del(id);
        return Result.success();
    }

    /**
     * 查询角色列表
     * @param ids ids
     * @return 角色列表
     */
    @GetMapping("/xmSelect")
    @ResponseBody
    public List<XmSelectNode> xmSelect(@RequestParam(value = "ids",required = false) Long[] ids) {
        List<XmSelectNode> sysMenus = sysRoleService.xmSelect(ids);
        return sysMenus;
    }
}
