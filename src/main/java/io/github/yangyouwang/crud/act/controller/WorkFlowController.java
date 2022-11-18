package io.github.yangyouwang.crud.act.controller;

import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.common.domain.TableDataInfo;
import io.github.yangyouwang.crud.act.model.StartDTO;
import io.github.yangyouwang.crud.act.service.WorkFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Objects;

/**
 * 工作流控制层
 */
@Controller
@RequestMapping("/workFlow")
@RequiredArgsConstructor
public class WorkFlowController {

    private static final String SUFFIX = "act/workFlow";

    private final WorkFlowService workflowService;

    /**
     * 跳转我的流程
     * @return 我的流程页面
     */
    @GetMapping("/flowPage")
    public String flowPage(){
        return SUFFIX + "/flow";
    }


    /**
     * 跳转代办任务
     * @return 代办任务页面
     */
    @GetMapping("/toDoTaskPage")
    public String toDoTaskPage(){
        return SUFFIX + "/toDoTask";
    }

    /**
     * 跳转已办任务
     * @return 已办任务页面
     */
    @GetMapping("/doneTaskPage")
    public String doneTaskPage(){
        return SUFFIX + "/doneTask";
    }

    /**
     * 启动流程
     * @return 启动流程页面
     */
    @GetMapping("/startPage")
    public String startPage(){
        return SUFFIX + "/start";
    }

    /**
     * 流程实例列表
     * @return 请求列表
     */
    @GetMapping("/myFlow")
    @ResponseBody
    public TableDataInfo myFlow(HttpServletRequest request) {
        int page = Integer.parseInt(request.getParameter("page")) - 1;
        int limit = Integer.parseInt(request.getParameter("limit"));
        return workflowService.myFlow(page, limit);
    }

    /**
     * 查询流程定义列表
     * @return 请求列表
     */
    @GetMapping("/flow")
    @ResponseBody
    public TableDataInfo flow(HttpServletRequest request) {
        int page = Integer.parseInt(request.getParameter("page")) - 1;
        int limit = Integer.parseInt(request.getParameter("limit"));
        return workflowService.flow(page, limit);
    }

    /**
     * 代办任务列表
     * @return 请求列表
     */
    @GetMapping("/toDoTask")
    @ResponseBody
    public TableDataInfo toDoTask(HttpServletRequest request) {
        int page = Integer.parseInt(request.getParameter("page")) - 1;
        int limit = Integer.parseInt(request.getParameter("limit"));
        return workflowService.toDoTask(page, limit);
    }

    /**
     * 已办任务列表
     * @return 请求列表
     */
    @GetMapping("/doneTask")
    @ResponseBody
    public TableDataInfo doneTask(HttpServletRequest request,
                              String name, String categoryId) {
        int page = Integer.parseInt(request.getParameter("page")) - 1;
        int limit = Integer.parseInt(request.getParameter("limit"));
        return workflowService.doneTask(page, limit, name, categoryId);
    }

    /**
     * 启动流程
     * @param startDTO 启动流程对象
     * @return 添加状态
     */
    @PostMapping("/start")
    @ResponseBody
    public Result start(@RequestBody @Valid StartDTO startDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        String flag = workflowService.start(startDTO);
        return Result.success(flag);
    }
}
