package io.github.yangyouwang.crud.act.service;

import io.github.yangyouwang.common.domain.TableDataInfo;
import io.github.yangyouwang.core.util.SecurityUtils;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description: 已办任务 <br/>
 * date: 2022/11/17 23:52<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Service
public class DoneTaskService {


    @Autowired
    private HistoryService historyService;

    public TableDataInfo list(int page, int limit, String name, String categoryId) {
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
        rspData.setData(query.listPage(page, limit));
        rspData.setCount(query.count());
        return rspData;
    }
}
