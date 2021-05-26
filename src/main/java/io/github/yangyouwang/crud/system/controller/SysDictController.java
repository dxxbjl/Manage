package io.github.yangyouwang.crud.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.crud.system.model.SysDictType;
import io.github.yangyouwang.crud.system.model.dto.SysDictValueDto;
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
import java.util.Objects;

/**
 * @author zhixin.yao
 * @version 1.0
 * @description: 字典控制层
 * @date 2021/4/12 11:00
 */
@Controller
@RequestMapping("/sysDict")
public class SysDictController {

    private static final String SUFFIX = "system/sysDict";

    private final SysDictService sysDictService;

    @Autowired
    public SysDictController(SysDictService sysDictService) {
        this.sysDictService = sysDictService;
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
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        IPage<SysDictType> list = sysDictService.list(sysDictListReq);
        return Result.success(list);
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
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        int flag = sysDictService.add(sysDictAddReq);
        return Result.success(flag);
    }

    /**
     * 编辑请求
     * @return 编辑状态
     */
    @PostMapping("/edit")
    @ResponseBody
    public Result edit(@RequestBody @Validated SysDictEditReq sysDictEditReq, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        int flag = sysDictService.edit(sysDictEditReq);
        return Result.success(flag);
    }

    /**
     * 修改字典状态
     * @return 修改状态
     */
    @PostMapping("/changeDict")
    @ResponseBody
    public Result changeDict(@RequestBody @Validated SysDictEnabledReq sysDictEnabledReq, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        int flag = sysDictService.changeDict(sysDictEnabledReq);
        return Result.success(flag);
    }

    /**
     * 删除请求
     * @return 删除状态
     */
    @DeleteMapping("/delKey/{id}")
    @ResponseBody
    public Result delKey(@Valid @NotNull(message = "id不能为空") @PathVariable Long id){
        int flag = sysDictService.delKey(id);
        return Result.success(flag);
    }

    /**
     * 删除字典值请求
     * @return 删除状态
     */
    @DeleteMapping("/delValue/{id}")
    @ResponseBody
    public Result delValue(@Valid @NotNull(message = "id不能为空") @PathVariable Long id){
        int flag = sysDictService.delValue(id);
        return Result.success(flag);
    }
}
