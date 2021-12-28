package io.github.yangyouwang.crud.system.controller;

import io.github.yangyouwang.common.annotation.CrudLog;
import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.common.domain.TreeSelectNode;
import io.github.yangyouwang.common.domain.XmSelectNode;
import io.github.yangyouwang.crud.system.model.req.SysMenuAddReq;
import io.github.yangyouwang.crud.system.model.req.SysMenuEditReq;
import io.github.yangyouwang.crud.system.model.req.SysMenuListReq;
import io.github.yangyouwang.crud.system.model.req.SysMenuVisibleReq;
import io.github.yangyouwang.crud.system.model.resp.SysMenuResp;
import io.github.yangyouwang.crud.system.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

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

    private static final String SUFFIX = "system/sysMenu";

    private final SysMenuService sysMenuService;

    @Autowired
    public SysMenuController(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }

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
    public String addPage(Long id, ModelMap map) {
        map.put("parentId",id);
        return SUFFIX + "/add";
    }

    /**
     * 跳转编辑
     * @param id 菜单id
     * @return 编辑页面
     */
    @GetMapping("/editPage/{id}")
    public String editPage(@Valid @NotNull(message = "id不能为空") @PathVariable Long id, ModelMap map){
        SysMenuResp sysMenu = sysMenuService.detail(id);
        map.put("sysMenu",sysMenu);
        return SUFFIX + "/edit";
    }

    /**
     * 列表请求
     * @param sysMenuListReq 请求菜单列表对象
     * @return 请求列表
     */
    @GetMapping("/list")
    @ResponseBody
    public Result list(SysMenuListReq sysMenuListReq) {
        List<SysMenuResp> list = sysMenuService.list(sysMenuListReq);
        return Result.success(list);
    }

    /**
     * 添加请求
     * @param sysMenuAddReq 添加菜单对象
     * @return 添加状态
     */
    @CrudLog
    @PostMapping("/add")
    @ResponseBody
    public Result add(@RequestBody @Validated SysMenuAddReq sysMenuAddReq, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        int flag = sysMenuService.add(sysMenuAddReq);
        return Result.success(flag);
    }

    /**
     * 编辑请求
     * @param sysMenuEditReq 编辑菜单对象
     * @return 编辑状态
     */
    @CrudLog
    @PostMapping("/edit")
    @ResponseBody
    public Result edit(@RequestBody @Validated SysMenuEditReq sysMenuEditReq, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        int flag = sysMenuService.edit(sysMenuEditReq);
        return Result.success(flag);
    }

    /**
     * 更新菜单状态
     * @param sysMenuVisibleReq 更新菜单对象
     * @return 菜单状态
     */
    @CrudLog
    @PostMapping("/changeMenu")
    @ResponseBody
    public Result changeMenu(@RequestBody @Validated SysMenuVisibleReq sysMenuVisibleReq, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        int flag = sysMenuService.changeMenu(sysMenuVisibleReq);
        return Result.success(flag);
    }

    /**
     * 删除请求
     * @param id 删除id
     * @return 删除状态
     */
    @CrudLog
    @DeleteMapping("/del/{id}")
    @ResponseBody
    public Result del(@Valid @NotNull(message = "id不能为空") @PathVariable Long id){
        sysMenuService.del(id);
        return Result.success();
    }

    /**
     * 查询菜单列表
     * @return 菜单列表
     */
    @GetMapping("/treeSelect")
    @ResponseBody
    public List<TreeSelectNode> treeSelect() {
        return sysMenuService.treeSelect();
    }

    /**
     * 查询菜单列表
     * @param ids 菜单id列表
     * @return 菜单列表
     */
    @GetMapping("/xmSelect")
    @ResponseBody
    public List<XmSelectNode> xmSelect(@RequestParam(value = "ids",required = false) Long[] ids) {
        return sysMenuService.xmSelect(ids);
    }
}