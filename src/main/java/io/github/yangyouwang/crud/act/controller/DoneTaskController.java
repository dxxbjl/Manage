package io.github.yangyouwang.crud.act.controller;

import io.github.yangyouwang.common.base.CrudController;
import io.github.yangyouwang.common.domain.TableDataInfo;
import io.github.yangyouwang.core.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.apache.commons.lang3.StringUtils;
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

    @Autowired
    private HistoryService historyService;

    private static final String SUFFIX = "act/doneTask";

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
        String userName = SecurityUtils.getUserName();
        HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery();
        query.involvedUser(userName);
        if (StringUtils.isNotBlank(name)) {
            query.processInstanceNameLike("%" + name + "%");
        }
        if (StringUtils.isNotBlank(categoryId)) {
            query.processDefinitionCategory(categoryId);
        }
        query.orderByProcessInstanceStartTime().desc();
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setData(query.listPage(Integer.parseInt(request.getParameter("page")),
                Integer.parseInt(request.getParameter("limit"))));
        rspData.setCount(query.count());
        return rspData;
    }
}
