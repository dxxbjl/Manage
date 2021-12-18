package io.github.yangyouwang.crud.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.yangyouwang.common.annotation.CrudLog;
import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.crud.system.model.req.*;
import io.github.yangyouwang.crud.system.model.resp.SysDictTypeResp;
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
     * @return 编辑页面
     */
    @GetMapping("/editPage/{id}")
    public String editPage(@Valid @NotNull(message = "id能为空") @PathVariable Long id, ModelMap map){
        SysDictTypeResp sysDict = sysDictTypeService.detail(id);
        map.put("sysDictType",sysDict);
        return SUFFIX + "/edit";
    }

    /**
     * 列表请求
     * @return 请求列表
     */
    @GetMapping("/list")
    @ResponseBody
    public Result list(@Validated SysDictTypeListReq sysDictListReq, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        IPage<SysDictTypeResp> list = sysDictTypeService.list(sysDictListReq);
        return Result.success(list);
    }

    /**
     * 添加请求
     * @return 添加状态
     */
    @CrudLog
    @PostMapping("/add")
    @ResponseBody
    public Result add(@RequestBody @Validated SysDictTypeAddReq sysDictAddReq, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        int flag = sysDictTypeService.add(sysDictAddReq);
        return Result.success(flag);
    }

    /**
     * 编辑请求
     * @return 编辑状态
     */
    @CrudLog
    @PostMapping("/edit")
    @ResponseBody
    public Result edit(@RequestBody @Validated SysDictTypeEditReq sysDictEditReq, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        int flag = sysDictTypeService.edit(sysDictEditReq);
        return Result.success(flag);
    }

    /**
     * 修改字典状态
     * @return 修改状态
     */
    @CrudLog
    @PostMapping("/changeDictType")
    @ResponseBody
    public Result changeDictType(@RequestBody @Validated SysDictTypeEnabledReq sysDictTypeEnabledReq, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        int flag = sysDictTypeService.changeDictType(sysDictTypeEnabledReq);
        return Result.success(flag);
    }

    /**
     * 删除请求
     * @return 删除状态
     */
    @CrudLog
    @DeleteMapping("/del/{id}")
    @ResponseBody
    public Result delKey(@Valid @NotNull(message = "id不能为空") @PathVariable Long id){
        int flag = sysDictTypeService.del(id);
        return Result.success(flag);
    }
}
