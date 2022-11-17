package io.github.yangyouwang.crud.act.controller;

import io.github.yangyouwang.common.base.CrudController;
import io.github.yangyouwang.common.domain.TableDataInfo;
import io.github.yangyouwang.core.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
/**
 * 代办任务
 */
@Controller
@RequestMapping("/toDoTask")
@RequiredArgsConstructor
public class ToDoTaskController extends CrudController {
    private static final String SUFFIX = "act/toDoTask";

    @Autowired
    private TaskService taskService;

    /**
     * 跳转列表
     * @return 列表页面
     */
    @GetMapping("/listPage")
    public String listPage(){
        return SUFFIX + "/list";
    }


    /**
     * 代办任务列表
     * @return 请求列表
     */
    @GetMapping("/page")
    @ResponseBody
    public TableDataInfo page(HttpServletRequest request) {
        String userName = SecurityUtils.getUserName();
        TaskQuery query = taskService.createTaskQuery()
                .taskCandidateOrAssigned(userName)
                .orderByTaskCreateTime().desc();
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setData(query.listPage(Integer.parseInt(request.getParameter("page")),
                Integer.parseInt(request.getParameter("limit"))));
        rspData.setCount(query.count());
        return rspData;
    }
}
