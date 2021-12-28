package io.github.yangyouwang.crud.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.yangyouwang.common.annotation.CrudLog;
import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.crud.system.model.req.SysLogListReq;
import io.github.yangyouwang.crud.system.model.resp.SysLogResp;
import io.github.yangyouwang.crud.system.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author yangyouwang
 * @title: SysLogController
 * @projectName crud
 * @description: 日志控制层
 * @date 2021/4/111:06 AM
 */
@Controller
@RequestMapping("/sysLog")
public class SysLogController {


    private static final String SUFFIX = "system/sysLog";

    private final SysLogService sysLogService;

    @Autowired
    public SysLogController(SysLogService sysLogService) {
        this.sysLogService = sysLogService;
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
     * 列表请求
     * @param sysLogListReq 日志列表对象
     * @return 请求列表
     */
    @GetMapping("/list")
    @ResponseBody
    public Result list(@Validated SysLogListReq sysLogListReq, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        IPage<SysLogResp> list = sysLogService.list(sysLogListReq);
        return Result.success(list);
    }

    /**
     * 删除请求
     * @param id 日志id
     * @return 删除状态
     */
    @CrudLog
    @DeleteMapping("/del/{id}")
    @ResponseBody
    public Result del(@Valid @NotNull(message = "id不能为空") @PathVariable Long id){
        sysLogService.removeById(id);
        return Result.success();
    }
}
