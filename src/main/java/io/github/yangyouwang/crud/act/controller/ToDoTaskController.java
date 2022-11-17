package io.github.yangyouwang.crud.act.controller;

import io.github.yangyouwang.common.base.CrudController;
import io.github.yangyouwang.common.domain.TableDataInfo;
import io.github.yangyouwang.crud.act.service.ToDoTaskService;
import lombok.RequiredArgsConstructor;
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
    private ToDoTaskService toDoTaskService;

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
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        return toDoTaskService.list(page, limit);
    }
}
