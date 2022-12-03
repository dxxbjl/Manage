package io.github.yangyouwang.crud.act.controller;

import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.common.domain.TableDataInfo;
import io.github.yangyouwang.crud.act.model.*;
import io.github.yangyouwang.crud.act.service.WorkFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * 工作流控制层
 * @author yangyouwang
 */
@Controller
@RequestMapping("/workFlow")
@RequiredArgsConstructor
public class WorkFlowController {

    private static final String SUFFIX = "act/workFlow";

    private final WorkFlowService workflowService;

    /**
     * 跳转我的流程
     */
    @GetMapping("/flowPage")
    public String flowPage(){
        return SUFFIX + "/flow";
    }

    /**
     * 跳转代办任务
     */
    @GetMapping("/toDoTaskPage")
    public String toDoTaskPage(){
        return SUFFIX + "/toDoTask";
    }

    /**
     * 跳转已办任务
     */
    @GetMapping("/historicTaskPage")
    public String historicTaskPage(){
        return SUFFIX + "/historicTask";
    }

    /**
     * 发起流程
     */
    @GetMapping("/startPage")
    public String startPage(){
        return SUFFIX + "/start";
    }

    /**
     * 审批历史页面
     */
    @GetMapping("/monitorPage")
    public String monitorPage(String processInstanceId, ModelMap map) {
        map.put("processInstanceId",processInstanceId);
        return SUFFIX + "/monitor";
    }

    /**
     * 审批页面
     */
    @GetMapping("/approvalPage")
    public String approvalPage(String processInstanceId, ModelMap map) {
        map.put("processInstanceId",processInstanceId);
        return SUFFIX + "/approval";
    }

    /**
     * 流程实例列表
     */
    @GetMapping("/myFlow")
    @ResponseBody
    public TableDataInfo myFlowList(int page, int limit, String name) {
        return workflowService.getMyFlowList(page, limit, name);
    }

    /**
     * 查询流程定义列表
     */
    @GetMapping("/flow")
    @ResponseBody
    public TableDataInfo flowList(int page, int limit, String name) {
        return workflowService.getFlowList(page, limit, name);
    }

    /**
     * 代办任务列表
     */
    @GetMapping("/toDoTask")
    @ResponseBody
    public TableDataInfo toDoTaskList(int page, int limit, String name) {
        return workflowService.getToDoTaskList(page, limit, name);
    }

    /**
     * 已办任务列表
     */
    @GetMapping("/historicTask")
    @ResponseBody
    public TableDataInfo historicTaskList(int page, int limit, String name) {
        return workflowService.getHistoricTaskList(page,limit,name);
    }

    /**
     * 获取启动表单
     * @param deploymentId 部署ID
     * @return 响应
     */
    @GetMapping("/getStartForm")
    @ResponseBody
    public Result getStartForm(String deploymentId) {
        FormVO formVO = workflowService.getStartForm(deploymentId);
        return Result.success("请填写表单信息",formVO);
    }

    /**
     * 获取任务表单
     * @param processInstanceId 流程实例ID
     * @return 响应
     */
    @GetMapping("/getTaskForm")
    @ResponseBody
    public Result getTaskForm(String processInstanceId) {
        FormVO formVO = workflowService.getTaskForm(processInstanceId);
        return Result.success("等待人员审批",formVO);
    }

    /**
     * 获取流程图片
     * @param deploymentId 部署ID
     */
    @GetMapping("/getFlowDiagram")
    @ResponseBody
    public void getFlowDiagram(String deploymentId, HttpServletResponse response) throws IOException {
        BufferedImage image = workflowService.getFlowDiagram(deploymentId);
        response.setContentType("image/png");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        ImageIO.write(image,"png",response.getOutputStream());
    }

    /**
     * 查看当前流程图
     * @param processInstanceId 流程实例ID
     */
    @GetMapping( "/getCurrentFlowDiagram")
    @ResponseBody
    public void getCurrentFlowDiagram(String processInstanceId, HttpServletResponse response) throws IOException {
        BufferedImage image = workflowService.getCurrentFlowDiagram(processInstanceId);
        response.setContentType("image/png");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        ImageIO.write(image,"png",response.getOutputStream());
    }

    /**
     * 查看审批历史
     * @param processInstanceId 流程实例ID
     */
    @GetMapping("/historic")
    @ResponseBody
    public Result historicList(String processInstanceId) {
        List<HistoricVO> historicVOList = workflowService.getHistoricListByProcessInstanceId(processInstanceId);
        return Result.success(historicVOList);
    }

    /**
     * 发起流程
     * @param startDTO 发起流程对象
     */
    @PostMapping("/start")
    @ResponseBody
    public Result start(@RequestBody @Valid StartDTO startDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        String businessKey = workflowService.start(startDTO);
        return Result.success("发起流程成功",businessKey);
    }

    /**
     * 完成任务
     * @param completeDTO 完成任务对象
     */
    @PostMapping("/complete")
    @ResponseBody
    public Result complete(@RequestBody @Valid CompleteDTO completeDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        workflowService.complete(completeDTO);
        return Result.success();
    }

    /**
     *  驳回任务
     * @param rejectDTO 驳回任务对象
     */
    @PostMapping("/reject")
    @ResponseBody
    public Result reject(@RequestBody @Valid RejectDTO rejectDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        workflowService.reject(rejectDTO);
        return Result.success();
    }

}
