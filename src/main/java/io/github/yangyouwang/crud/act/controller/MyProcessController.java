package io.github.yangyouwang.crud.act.controller;

import io.github.yangyouwang.common.base.CrudController;
import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.common.domain.TableDataInfo;
import io.github.yangyouwang.crud.act.model.StartProcessDTO;
import io.github.yangyouwang.crud.act.service.MyProcessService;
import lombok.RequiredArgsConstructor;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;


/**
 * 我的流程
 */
@Controller
@RequestMapping("/myProcess")
@RequiredArgsConstructor
public class MyProcessController extends CrudController {

    private static final String SUFFIX = "act/myProcess";

    @Autowired
    private MyProcessService myProcessService;

    /**
     * 跳转列表
     * @return 列表页面
     */
    @GetMapping("/listPage")
    public String listPage(){
        return SUFFIX + "/list";
    }


    /**
     * 流程实例列表
     * @return 请求列表
     */
    @GetMapping("/taskPage")
    @ResponseBody
    public TableDataInfo taskPage(HttpServletRequest request) {
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        return myProcessService.taskPage(page, limit);
    }

    /**
     * 查询流程定义列表
     * @return 请求列表
     */
    @GetMapping("/processPage")
    @ResponseBody
    public List<ProcessDefinition> processPage(String category) {
        return myProcessService.processList(category);
    }


    /**
     * 启动流程
     * @return 启动流程页面
     */
    @GetMapping("/addPage")
    public String addPage(){
        return SUFFIX + "/add";
    }

    /**
     * 启动流程
     * @param startProcessDTO 启动流程对象
     * @return 添加状态
     */
    @PostMapping("/start")
    @ResponseBody
    public Result start(@RequestBody @Valid StartProcessDTO startProcessDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        String flag = myProcessService.start(startProcessDTO);
        return Result.success(flag);
    }
}
