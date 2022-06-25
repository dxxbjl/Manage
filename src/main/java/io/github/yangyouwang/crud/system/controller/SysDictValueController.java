package io.github.yangyouwang.crud.system.controller;

import io.github.yangyouwang.common.annotation.CrudLog;
import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.common.domain.EnabledDTO;
import io.github.yangyouwang.crud.system.model.params.SysDictValueAddDTO;
import io.github.yangyouwang.crud.system.model.params.SysDictValueEditDTO;
import io.github.yangyouwang.crud.system.model.result.SysDictValueDTO;
import io.github.yangyouwang.crud.system.service.SysDictValueService;
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
@RequestMapping("/sysDictValue")
public class SysDictValueController {

    private static final String SUFFIX = "system/sysDictValue";

    private final SysDictValueService sysDictValueService;

    @Autowired
    public SysDictValueController(SysDictValueService sysDictValueService) {
        this.sysDictValueService = sysDictValueService;
    }

    /**
     * 删除字典值请求
     * @param id 字典id
     * @return 删除状态
     */
    @CrudLog
    @DeleteMapping("/del/{id}")
    @ResponseBody
    public Result del(@Valid @NotNull(message = "id不能为空") @PathVariable Long id){
        sysDictValueService.removeById(id);
        return Result.success();
    }

    /**
     * 跳转添加
     * @param id 字典类型id
     * @return 添加页面
     */
    @GetMapping("/addPage/{id}")
    public String addPage(@Valid @NotNull(message = "dictTypeId不能为空") @PathVariable Long id, ModelMap map){
        map.put("dictTypeId",id);
        return SUFFIX + "/add";
    }

    /**
     * 跳转编辑
     * @param id 字典值id
     * @return 编辑页面
     */
    @GetMapping("/editPage/{id}")
    public String editPage(@Valid @NotNull(message = "id能为空") @PathVariable Long id, ModelMap map){
        SysDictValueDTO sysDictValue = sysDictValueService.detail(id);
        map.put("sysDictValue",sysDictValue);
        return SUFFIX + "/edit";
    }

    /**
     * 添加请求
     * @param sysDictValueAddDTO 添加字典值对象
     * @return 添加状态
     */
    @CrudLog
    @PostMapping("/add")
    @ResponseBody
    public Result add(@RequestBody @Validated SysDictValueAddDTO sysDictValueAddDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        boolean flag = sysDictValueService.add(sysDictValueAddDTO);
        return Result.success(flag);
    }

    /**
     * 编辑请求
     * @param sysDictValueEditDTO 编辑字典值对象
     * @return 编辑状态
     */
    @CrudLog
    @PostMapping("/edit")
    @ResponseBody
    public Result edit(@RequestBody @Validated SysDictValueEditDTO sysDictValueEditDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        boolean flag = sysDictValueService.edit(sysDictValueEditDTO);
        return Result.success(flag);
    }

    /**
     * 修改字典值状态
     * @param enabledDTO 修改字典值参数
     * @return 修改状态
     */
    @CrudLog
    @PostMapping("/changeDictValue")
    @ResponseBody
    public Result changeDictValue(@RequestBody @Validated EnabledDTO enabledDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        boolean flag = sysDictValueService.changeDictValue(enabledDTO);
        return Result.success(flag);
    }

}
