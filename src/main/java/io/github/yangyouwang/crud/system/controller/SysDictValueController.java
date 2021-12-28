package io.github.yangyouwang.crud.system.controller;

import io.github.yangyouwang.common.annotation.CrudLog;
import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.crud.system.model.req.SysDictValueAddReq;
import io.github.yangyouwang.crud.system.model.req.SysDictValueEditReq;
import io.github.yangyouwang.crud.system.model.resp.SysDictValueResp;
import io.github.yangyouwang.crud.system.service.SysDictValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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
@RequestMapping("/sysDictValue")
public class SysDictValueController {

    private static final String SUFFIX = "system/sysDictValue";

    private final SysDictValueService sysDictValueService;

    @Autowired
    public SysDictValueController(SysDictValueService sysDictValueService) {
        this.sysDictValueService = sysDictValueService;
    }

    /**
     * 根据字典类型获取字典值列表
     * @return 请求列表
     */
    @GetMapping("/getDictValues/{dictKey}")
    @ResponseBody
    public Result getDictValues(@Valid @NotBlank(message = "字典类型不能为空") @PathVariable String dictKey) {
        List<SysDictValueResp> sysDictValueResps = sysDictValueService.getDictValues(dictKey);
        return Result.success(sysDictValueResps);
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
        SysDictValueResp sysDictValue = sysDictValueService.detail(id);
        map.put("sysDictValue",sysDictValue);
        return SUFFIX + "/edit";
    }

    /**
     * 添加请求
     * @param sysDictValueAddReq 添加字典值对象
     * @return 添加状态
     */
    @CrudLog
    @PostMapping("/add")
    @ResponseBody
    public Result add(@RequestBody @Validated SysDictValueAddReq sysDictValueAddReq, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        int flag = sysDictValueService.add(sysDictValueAddReq);
        return Result.success(flag);
    }

    /**
     * 编辑请求
     * @param sysDictValueEditReq 编辑字典值对象
     * @return 编辑状态
     */
    @CrudLog
    @PostMapping("/edit")
    @ResponseBody
    public Result edit(@RequestBody @Validated SysDictValueEditReq sysDictValueEditReq, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        int flag = sysDictValueService.edit(sysDictValueEditReq);
        return Result.success(flag);
    }
}
