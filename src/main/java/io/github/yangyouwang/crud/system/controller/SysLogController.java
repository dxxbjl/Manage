package io.github.yangyouwang.crud.system.controller;

import io.github.yangyouwang.common.annotation.CrudLog;
import io.github.yangyouwang.common.base.CrudController;
import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.common.domain.TableDataInfo;
import io.github.yangyouwang.crud.system.entity.SysLog;
import io.github.yangyouwang.crud.system.service.SysLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author yangyouwang
 * @title: SysLogController
 * @projectName crud
 * @description: 日志控制层
 * @date 2021/4/111:06 AM
 */
@Controller
@RequestMapping("/sysLog")
@RequiredArgsConstructor
public class SysLogController extends CrudController {


    private static final String SUFFIX = "system/sysLog";

    private final SysLogService sysLogService;

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
     * @param sysLog 日志列表对象
     * @return 请求列表
     */
    @GetMapping("/page")
    @ResponseBody
    public TableDataInfo page(SysLog sysLog) {
        startPage();
        List<SysLog> list = sysLogService.list(sysLog);
        return getDataTable(list);
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
        boolean flag = sysLogService.removeById(id);
        return Result.success(flag);
    }
}
