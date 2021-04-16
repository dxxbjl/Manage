package io.github.yangyouwang.crud.system.controller;

import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.crud.system.model.dao.SysDictValueDto;
import io.github.yangyouwang.crud.system.model.req.*;
import io.github.yangyouwang.crud.system.model.resp.SysDictResp;
import io.github.yangyouwang.crud.system.service.SysDictService;
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

/**
 * @author zhixin.yao
 * @version 1.0
 * @description: 字典控制层
 * @date 2021/4/12 11:00
 */
@Controller
@RequestMapping("/sysDict")
public class SysDictController {

    private static final String SUFFIX = "/system/sysDict";

    @Autowired
    private SysDictService sysDictService;

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
        SysDictResp sysDict = sysDictService.detail(id);
        map.put("sysDict",sysDict);
        return SUFFIX + "/edit";
    }

    /**
     * 列表请求
     * @return 请求列表
     */
    @GetMapping("/list")
    @ResponseBody
    public Result list(@Validated SysDictListReq sysDictListReq, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
        }
        return Result.success(sysDictService.list(sysDictListReq));
    }

    /**
     * 根据字典类型获取字典值列表
     * @return 请求列表
     */
    @GetMapping("/getDictValues/{dictKey}")
    @ResponseBody
    public Result getDictValues(@Valid @NotBlank(message = "字典类型不能为空") @PathVariable String dictKey) {
        List<SysDictValueDto> sysDictValueDtos = sysDictService.getDictValues(dictKey);
        return Result.success(sysDictValueDtos);
    }

    /**
     * 添加请求
     * @return 添加状态
     */
    @PostMapping("/add")
    @ResponseBody
    public Result add(@RequestBody @Validated SysDictAddReq sysDictAddReq, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
        }
        sysDictService.add(sysDictAddReq);
        return Result.success();
    }

    /**
     * 编辑请求
     * @return 编辑状态
     */
    @PostMapping("/edit")
    @ResponseBody
    public Result edit(@RequestBody @Validated SysDictEditReq sysDictEditReq, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
        }
        sysDictService.edit(sysDictEditReq);
        return Result.success();
    }

    /**
     * 删除请求
     * @return 删除状态
     */
    @DeleteMapping("/del/{id}")
    @ResponseBody
    public Result del(@Valid @NotNull(message = "id不能为空") @PathVariable Long id){
        sysDictService.del(id);
        return Result.success();
    }
}
