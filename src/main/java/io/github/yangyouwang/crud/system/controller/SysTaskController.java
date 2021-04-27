package io.github.yangyouwang.crud.system.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.crud.system.model.SysTask;
import io.github.yangyouwang.crud.system.model.req.SysTaskAddReq;
import io.github.yangyouwang.crud.system.model.req.SysTaskEditReq;
import io.github.yangyouwang.crud.system.model.req.SysTaskEnabledReq;
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
import java.util.Objects;


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

    private final SysTaskService sysTaskService;

    @Autowired
    public SysTaskController(SysTaskService sysTaskService) {
        this.sysTaskService = sysTaskService;
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
        if (bindingResult.hasErrors()) {
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        IPage<SysTask> list = sysTaskService.list(sysRoleListReq);
        return Result.success(list);
    }

    /**
     * 添加请求
     * @return 添加状态
     */
    @PostMapping("/add")
    @ResponseBody
    public Result add(@RequestBody @Validated SysTaskAddReq sysTaskAddReq, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        int flag = sysTaskService.add(sysTaskAddReq);
        return Result.success(flag);
    }

    /**
     * 编辑请求
     * @return 编辑状态
     */
    @PostMapping("/edit")
    @ResponseBody
    public Result edit(@RequestBody @Validated SysTaskEditReq sysTaskEditReq, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        SysTask sysTask = new SysTask();
        BeanUtil.copyProperties(sysTaskEditReq,sysTask,true, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        int flag = sysTaskService.edit(sysTask);
        return Result.success(flag);
    }

    /**
     * 修改任务请求
     * @return 修改状态
     */
    @PostMapping("/changeTask")
    @ResponseBody
    public Result changeTask(@RequestBody @Validated SysTaskEnabledReq sysTaskEnabledReq, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        SysTask sysTask = sysTaskService.findTaskById(sysTaskEnabledReq.getId());
        sysTask.setEnabled(sysTaskEnabledReq.getEnabled());
        int flag = sysTaskService.edit(sysTask);
        return Result.success(flag);
    }

    /**
     * 删除请求
     * @return 删除状态
     */
    @DeleteMapping("/del/{id}")
    @ResponseBody
    public Result del(@Valid @NotNull(message = "id不能为空") @PathVariable Long id){
        int flag = sysTaskService.del(id);
        return Result.success(flag);
    }
}
