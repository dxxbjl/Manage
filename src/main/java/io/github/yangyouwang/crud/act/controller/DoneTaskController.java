package io.github.yangyouwang.crud.act.controller;

import io.github.yangyouwang.common.base.CrudController;
import io.github.yangyouwang.common.domain.TableDataInfo;
import io.github.yangyouwang.crud.act.service.DoneTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 已办任务
 */
@Controller
@RequestMapping("/doneTask")
@RequiredArgsConstructor
public class DoneTaskController extends CrudController {

    private static final String SUFFIX = "act/doneTask";

    @Autowired
    private DoneTaskService doneTaskService;

    /**
     * 跳转列表
     * @return 列表页面
     */
    @GetMapping("/listPage")
    public String listPage(){
        return SUFFIX + "/list";
    }


    /**
     * 已办任务列表
     * @return 请求列表
     */
    @GetMapping("/page")
    @ResponseBody
    public TableDataInfo page(HttpServletRequest request,
                              String name, String categoryId) {
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        return doneTaskService.list(page, limit, name, categoryId);
    }
}
