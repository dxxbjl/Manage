package io.github.yangyouwang.crud.act.controller;

import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.common.domain.TableDataInfo;
import io.github.yangyouwang.crud.act.model.FormVO;
import io.github.yangyouwang.crud.act.model.StartDTO;
import io.github.yangyouwang.crud.act.service.WorkFlowService;
import lombok.RequiredArgsConstructor;
import org.activiti.engine.form.FormProperty;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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
    @GetMapping("/historicTaskPage")
    public String historicTaskPage(){
        return SUFFIX + "/historicTask";
    }

    /**
     * 发起流程
     * @return 发起流程页面
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
    public TableDataInfo myFlow(String name, HttpServletRequest request) {
        int page = Integer.parseInt(request.getParameter("page")) - 1;
        int limit = Integer.parseInt(request.getParameter("limit"));
        return workflowService.myFlow(name, page, limit);
    }

    /**
     * 查询流程定义列表
     * @return 请求列表
     */
    @GetMapping("/flow")
    @ResponseBody
    public TableDataInfo flow(String name, String key, String category, HttpServletRequest request) {
        int page = Integer.parseInt(request.getParameter("page")) - 1;
        int limit = Integer.parseInt(request.getParameter("limit"));
        return workflowService.flow(name, key, category, page, limit);
    }

    /**
     * 代办任务列表
     * @return 请求列表
     */
    @GetMapping("/toDoTask")
    @ResponseBody
    public TableDataInfo toDoTask(HttpServletRequest request, String name) {
        int page = Integer.parseInt(request.getParameter("page")) - 1;
        int limit = Integer.parseInt(request.getParameter("limit"));
        return workflowService.toDoTask(page, limit, name);
    }

    /**
     * 已办任务列表
     * @return 请求列表
     */
    @GetMapping("/historicTask")
    @ResponseBody
    public TableDataInfo historicTask(HttpServletRequest request, String name) {
        int page = Integer.parseInt(request.getParameter("page")) - 1;
        int limit = Integer.parseInt(request.getParameter("limit"));
        return workflowService.historicTask(page, limit, name);
    }

    /**
     * 获取启动流程表单
     * @param deploymentId 部署ID
     * @return 添加状态
     */
    @GetMapping("/getStartFlowForm")
    @ResponseBody
    public Result getStartFlowForm(@RequestParam @NotBlank(message = "部署ID不为空") String deploymentId) {
        FormVO formVO = workflowService.getStartFlowForm(deploymentId);
        return Result.success("请填写表单信息",formVO);
    }

    /**
     * 获取流程图片
     * @param deploymentId 部署ID
     * @return 添加状态
     */
    @GetMapping("/getFlowDiagram")
    @ResponseBody
    public void getFlowDiagram(@RequestParam @NotBlank(message = "部署ID不为空") String deploymentId,
                               HttpServletResponse response) throws IOException {
        BufferedImage image = workflowService.getFlowDiagram(deploymentId);
        response.setContentType("image/png");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        ImageIO.write(image,"png",response.getOutputStream());
    }

    /**
     * 发起流程
     * @param startDTO 发起流程对象
     * @return 添加状态
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
}
