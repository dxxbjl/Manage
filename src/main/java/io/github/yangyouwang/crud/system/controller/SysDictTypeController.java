package io.github.yangyouwang.crud.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.yangyouwang.common.annotation.CrudLog;
import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.crud.system.model.params.*;
import io.github.yangyouwang.crud.system.model.result.SysDictTypeDTO;
import io.github.yangyouwang.crud.system.service.SysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author zhixin.yao
 * @version 1.0
 * @description: 字典控制层
 * @date 2021/4/12 11:00
 */
@Controller
@RequestMapping("/sysDictType")
public class SysDictTypeController {

    private static final String SUFFIX = "system/sysDictType";

    private final SysDictTypeService sysDictTypeService;

    @Autowired
    public SysDictTypeController(SysDictTypeService sysDictTypeService) {
        this.sysDictTypeService = sysDictTypeService;
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
     * @param id 字典id
     * @return 编辑页面
     */
    @GetMapping("/editPage/{id}")
    public String editPage(@Valid @NotNull(message = "id能为空") @PathVariable Long id, ModelMap map){
        SysDictTypeDTO sysDict = sysDictTypeService.detail(id);
        map.put("sysDictType",sysDict);
        return SUFFIX + "/edit";
    }

    /**
     * 列表请求
     * @param sysDictTypeListDTO 请求字典列表参数
     * @return 请求列表
     */
    @GetMapping("/list")
    @ResponseBody
    public Result list(@Validated SysDictTypeListDTO sysDictTypeListDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        IPage<SysDictTypeDTO> list = sysDictTypeService.list(sysDictTypeListDTO);
        return Result.success(list);
    }

    /**
     * 添加请求
     * @param sysDictTypeAddDTO 添加字典参数
     * @return 添加状态
     */
    @CrudLog
    @PostMapping("/add")
    @ResponseBody
    public Result add(@RequestBody @Validated SysDictTypeAddDTO sysDictTypeAddDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        boolean flag = sysDictTypeService.add(sysDictTypeAddDTO);
        return Result.success(flag);
    }

    /**
     * 编辑请求
     * @param sysDictTypeEditDTO 编辑字典参数
     * @return 编辑状态
     */
    @CrudLog
    @PostMapping("/edit")
    @ResponseBody
    public Result edit(@RequestBody @Validated SysDictTypeEditDTO sysDictTypeEditDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        boolean flag = sysDictTypeService.edit(sysDictTypeEditDTO);
        return Result.success(flag);
    }

    /**
     * 修改字典状态
     * @param sysDictTypeEnabledDTO 修改字典参数
     * @return 修改状态
     */
    @CrudLog
    @PostMapping("/changeDictType")
    @ResponseBody
    public Result changeDictType(@RequestBody @Validated SysDictTypeEnabledDTO sysDictTypeEnabledDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        boolean flag = sysDictTypeService.changeDictType(sysDictTypeEnabledDTO);
        return Result.success(flag);
    }

    /**
     * 删除请求
     * @param id 字典id
     * @return 删除状态
     */
    @CrudLog
    @DeleteMapping("/del/{id}")
    @ResponseBody
    public Result delKey(@Valid @NotNull(message = "id不能为空") @PathVariable Long id){
        sysDictTypeService.del(id);
        return Result.success();
    }
}
