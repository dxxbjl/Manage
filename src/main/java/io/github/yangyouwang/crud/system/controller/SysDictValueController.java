package io.github.yangyouwang.crud.system.controller;

import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.crud.system.model.dto.SysDictValueDto;
import io.github.yangyouwang.crud.system.service.SysDictValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/sysDictValue")
public class SysDictValueController {

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
        List<SysDictValueDto> sysDictValueDtos = sysDictValueService.getDictValues(dictKey);
        return Result.success(sysDictValueDtos);
    }

    /**
     * 删除字典值请求
     * @return 删除状态
     */
    @DeleteMapping("/del/{id}")
    @ResponseBody
    public Result del(@Valid @NotNull(message = "id不能为空") @PathVariable Long id){
        int flag = sysDictValueService.del(id);
        return Result.success(flag);
    }
}
