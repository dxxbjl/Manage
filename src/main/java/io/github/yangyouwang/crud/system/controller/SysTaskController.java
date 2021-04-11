package io.github.yangyouwang.crud.system.controller;

import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.crud.system.model.req.SysTaskAddReq;
import io.github.yangyouwang.crud.system.model.req.SysTaskEditReq;
import io.github.yangyouwang.crud.system.model.req.SysTaskListReq;
import io.github.yangyouwang.crud.system.model.resp.SysTaskResp;
import io.github.yangyouwang.crud.system.service.SysTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


/**
 * @author yangyouwang
 * @title: SysTaskController
 * @projectName crud
 * @description: 任务控制层
 * @date 2021/4/10上午10:25
 */
@Controller
@RequestMapping("/sysTask")
public class SysTaskController {

    private static final String SUFFIX = "/system/sysTask";

    @Autowired
    private SysTaskService sysTaskService;

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
    public String editPage(@Valid @NotNull(message = "id不能为空") @PathVariable Long id, ModelMap map){
        SysTaskResp sysTask = sysTaskService.detail(id);
        map.put("sysTask",sysTask);
        return SUFFIX + "/edit";
    }


    /**
     * 列表请求
     * @return 请求列表
     */
    @GetMapping("/list")
    @ResponseBody
    public Result list(@Validated SysTaskListReq sysRoleListReq, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
        }
        return Result.success(sysTaskService.list(sysRoleListReq));
    }

    /**
     * 添加请求
     * @return 添加状态
     */
    @PostMapping("/add")
    @ResponseBody
    public Result add(@RequestBody @Validated SysTaskAddReq sysTaskAddReq, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
        }
        sysTaskService.add(sysTaskAddReq);
        return Result.success();
    }

    /**
     * 编辑请求
     * @return 编辑状态
     */
    @PostMapping("/edit")
    @ResponseBody
    public Result edit(@RequestBody @Validated SysTaskEditReq sysTaskEditReq, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
        }
        sysTaskService.edit(sysTaskEditReq);
        return Result.success();
    }

    /**
     * 删除请求
     * @return 删除状态
     */
    @DeleteMapping("/del/{id}")
    @ResponseBody
    public Result del(@Valid @NotNull(message = "id不能为空") @PathVariable Long id){
        sysTaskService.del(id);
        return Result.success();
    }
}
