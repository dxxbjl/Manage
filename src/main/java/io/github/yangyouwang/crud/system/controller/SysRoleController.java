package io.github.yangyouwang.crud.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.yangyouwang.common.annotation.CrudLog;
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
import java.util.Objects;

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

    private static final String SUFFIX = "system/sysRole";

    private final SysRoleService sysRoleService;

    @Autowired
    public SysRoleController(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
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
    public String addPage(){
        return SUFFIX + "/add";
    }

    /**
     * 跳转编辑
     * @param id 角色id
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
     * @param sysRoleListReq 请求角色列表对象
     * @return 请求列表
     */
    @GetMapping("/list")
    @ResponseBody
    public Result list(@Validated SysRoleListReq sysRoleListReq, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        IPage<SysRoleResp> list = sysRoleService.list(sysRoleListReq);
        return Result.success(list);
    }

    /**
     * 添加请求
     * @param sysRoleAddReq 添加角色对象
     * @return 添加状态
     */
    @CrudLog
    @PostMapping("/add")
    @ResponseBody
    public Result add(@RequestBody @Validated SysRoleAddReq sysRoleAddReq, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        int flag = sysRoleService.add(sysRoleAddReq);
        return Result.success(flag);
    }

    /**
     * 编辑请求
     * @param sysRoleEditReq 编辑角色对象
     * @return 编辑状态
     */
    @CrudLog
    @PostMapping("/edit")
    @ResponseBody
    public Result edit(@RequestBody @Validated SysRoleEditReq sysRoleEditReq, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        int flag = sysRoleService.edit(sysRoleEditReq);
        return Result.success(flag);
    }

    /**
     * 删除请求
     * @param id 角色id
     * @return 删除状态
     */
    @CrudLog
    @DeleteMapping("/del/{id}")
    @ResponseBody
    public Result del(@Valid @NotNull(message = "id不能为空") @PathVariable Long id){
        sysRoleService.del(id);
        return Result.success();
    }

    /**
     * 查询角色列表
     * @param ids 角色ids
     * @return 角色列表
     */
    @GetMapping("/xmSelect")
    @ResponseBody
    public List<XmSelectNode> xmSelect(@RequestParam(value = "ids",required = false) Long[] ids) {
        return sysRoleService.xmSelect(ids);
    }
}
