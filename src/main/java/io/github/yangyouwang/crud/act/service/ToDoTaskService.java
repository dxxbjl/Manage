package io.github.yangyouwang.crud.act.service;

import io.github.yangyouwang.common.domain.TableDataInfo;
import io.github.yangyouwang.core.util.SecurityUtils;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description: 代办任务 <br/>
 * date: 2022/11/17 23:49<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Service
public class ToDoTaskService {

    @Autowired
    private TaskService taskService;

    public TableDataInfo list(int page, int limit) {
        String userName = SecurityUtils.getUserName();
        TaskQuery query = taskService.createTaskQuery()
                .taskCandidateOrAssigned(userName)
                .orderByTaskCreateTime().desc();
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setData(query.listPage(page, limit));
        rspData.setCount(query.count());
        return rspData;
    }
}
