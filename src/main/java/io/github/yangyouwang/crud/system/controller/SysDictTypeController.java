package io.github.yangyouwang.crud.system.controller;

import io.github.yangyouwang.common.annotation.CrudLog;
import io.github.yangyouwang.common.base.CrudController;
import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.common.domain.TableDataInfo;
import io.github.yangyouwang.common.enums.BusinessType;
import io.github.yangyouwang.crud.system.entity.SysDictType;
import io.github.yangyouwang.crud.system.service.SysDictTypeService;
import lombok.RequiredArgsConstructor;
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
 * @author zhixin.yao
 * @version 1.0
 * @description: 字典控制层
 * @date 2021/4/12 11:00
 */
@Controller
@RequestMapping("/sysDictType")
@RequiredArgsConstructor
public class SysDictTypeController extends CrudController {

    private static final String SUFFIX = "system/sysDictType";

    private final SysDictTypeService sysDictTypeService;

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
     * @param id 字典id
     * @return 编辑页面
     */
    @GetMapping("/editPage/{id}")
    public String editPage(@Valid @NotNull(message = "id能为空") @PathVariable Long id, ModelMap map){
        SysDictType sysDict = sysDictTypeService.getById(id);
        map.put("sysDictType",sysDict);
        return SUFFIX + "/edit";
    }

    /**
     * 列表请求
     * @param sysDictType 请求字典列表参数
     * @return 请求列表
     */
    @CrudLog(title = "查询字典类型分页列表",businessType = BusinessType.SELECT)
    @GetMapping("/page")
    @ResponseBody
    public TableDataInfo page(SysDictType sysDictType) {
        startPage();
        List<SysDictType> list = sysDictTypeService.list(sysDictType);
        return getDataTable(list);
    }

    /**
     * 添加请求
     * @param sysDictType 添加字典参数
     * @return 添加状态
     */
    @CrudLog(title = "添加字典类型",businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public Result add(@RequestBody @Validated SysDictType sysDictType, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        boolean flag = sysDictTypeService.add(sysDictType);
        return Result.success(flag);
    }

    /**
     * 编辑请求
     * @param sysDictType 编辑字典参数
     * @return 编辑状态
     */
    @CrudLog(title = "更新字典类型",businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public Result edit(@RequestBody @Validated SysDictType sysDictType, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        boolean flag = sysDictTypeService.updateById(sysDictType);
        return Result.success(flag);
    }

    /**
     * 修改字典状态
     * @param sysDictType 修改字典参数
     * @return 修改状态
     */
    @CrudLog(title = "更新字典类型状态",businessType = BusinessType.UPDATE)
    @PostMapping("/changeDictType")
    @ResponseBody
    public Result changeDictType(@RequestBody @Validated SysDictType sysDictType, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        boolean flag = sysDictTypeService.updateById(sysDictType);
        return Result.success(flag);
    }

    /**
     * 删除请求
     * @param id 字典id
     * @return 删除状态
     */
    @CrudLog(title = "删除字典类型",businessType = BusinessType.DELETE)
    @DeleteMapping("/del/{id}")
    @ResponseBody
    public Result delKey(@Valid @NotNull(message = "id不能为空") @PathVariable Long id){
        sysDictTypeService.del(id);
        return Result.success();
    }
}
