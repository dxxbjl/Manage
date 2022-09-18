package io.github.yangyouwang.crud.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.yangyouwang.common.annotation.CrudLog;
import io.github.yangyouwang.common.base.CrudController;
import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.common.domain.TableDataInfo;
import io.github.yangyouwang.common.domain.XmSelectNode;
import io.github.yangyouwang.common.enums.BusinessType;
import io.github.yangyouwang.crud.system.entity.SysRole;
import io.github.yangyouwang.crud.system.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequiredArgsConstructor
public class SysRoleController extends CrudController {

    private static final String SUFFIX = "system/sysRole";

    private final SysRoleService sysRoleService;

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
        SysRole sysRole = sysRoleService.detail(id);
        map.put("sysRole",sysRole);
        return SUFFIX + "/edit";
    }


    /**
     * 列表请求
     * @param sysRole 请求角色列表对象
     * @return 请求列表
     */
    @PreAuthorize("hasAuthority('role:list')")
    @GetMapping("/page")
    @ResponseBody
    public TableDataInfo page(@Validated SysRole sysRole) {
        startPage();
        List<SysRole> list = sysRoleService.list(new LambdaQueryWrapper<SysRole>()
                .like(StringUtils.isNotBlank(sysRole.getRoleName()), SysRole::getRoleName , sysRole.getRoleName())
                .like(StringUtils.isNotBlank(sysRole.getRoleKey()), SysRole::getRoleKey , sysRole.getRoleKey())
                .orderByDesc(SysRole::getCreateTime));
        return getDataTable(list);
    }

    /**
     * 添加请求
     * @param sysRole 添加角色对象
     * @return 添加状态
     */
    @CrudLog(title = "添加角色",businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public Result add(@RequestBody @Validated SysRole sysRole, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        sysRoleService.add(sysRole);
        return Result.success();
    }

    /**
     * 编辑请求
     * @param sysRole 编辑角色对象
     * @return 编辑状态
     */
    @CrudLog(title = "更新角色",businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public Result edit(@RequestBody @Validated SysRole sysRole, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        sysRoleService.edit(sysRole);
        return Result.success();
    }

    /**
     * 删除请求
     * @param id 角色id
     * @return 删除状态
     */
    @CrudLog(title = "删除角色",businessType = BusinessType.DELETE)
    @DeleteMapping("/del/{id}")
    @ResponseBody
    public Result del(@Valid @NotNull(message = "id不能为空") @PathVariable Long id){
        sysRoleService.del(id);
        return Result.success();
    }

    /**
     * 根据角色ids查询选中角色列表
     * @param ids 角色ids
     * @return 角色列表
     */
    @GetMapping("/xmSelect")
    @ResponseBody
    public List<XmSelectNode> xmSelect(@RequestParam(value = "ids",required = false) String ids) {
        return sysRoleService.xmSelect(ids);
    }
}
