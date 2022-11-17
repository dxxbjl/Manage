package io.github.yangyouwang.crud.act.service;

import io.github.yangyouwang.common.domain.TableDataInfo;
import io.github.yangyouwang.core.util.SecurityUtils;
import io.github.yangyouwang.crud.act.model.StartProcessDTO;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description: 我的流程 <br/>
 * date: 2022/11/17 23:15<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Service
public class MyProcessService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    public String start(StartProcessDTO startProcessDTO) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(startProcessDTO.getDeploymentId()).singleResult();
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), "abc");
        return processInstance.getProcessInstanceId();
    }

    public TableDataInfo taskPage(int page, int limit) {
        String userName = SecurityUtils.getUserName();
        TaskQuery query = taskService.createTaskQuery()
                .taskAssignee(userName)
                .orderByTaskCreateTime().desc();
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setData(query.listPage(page,limit));
        rspData.setCount(query.count());
        return rspData;
    }

    public List<ProcessDefinition> processList(String category) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
                .processDefinitionCategory(category)
                .orderByProcessDefinitionVersion().asc();
        return processDefinitionQuery.list();
    }
}
