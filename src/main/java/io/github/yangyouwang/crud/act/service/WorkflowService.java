package io.github.yangyouwang.crud.act.service;

import io.github.yangyouwang.common.domain.TableDataInfo;
import io.github.yangyouwang.core.util.SecurityUtils;
import io.github.yangyouwang.crud.act.model.FlowVO;
import io.github.yangyouwang.crud.act.model.StartDTO;
import io.github.yangyouwang.crud.act.model.TaskVO;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 工作流业务层
 */
@Service
public class WorkflowService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    public TableDataInfo myFlow(int page, int limit) {
        String userName = SecurityUtils.getUserName();
        TaskQuery query = taskService.createTaskQuery()
                .taskAssignee(userName)
                .orderByTaskCreateTime().desc();
        List<Task> tasks = query.listPage(page, limit);
        List<TaskVO> taskVOList = tasks.stream().map(s -> {
            TaskVO taskVO = new TaskVO();
            taskVO.setId(s.getId());
            taskVO.setCategory(s.getCategory());
            taskVO.setName(s.getName());
            taskVO.setAssignee(s.getAssignee());
            taskVO.setDescription(s.getDescription());
            taskVO.setCreateTime(s.getCreateTime());
            return taskVO;
        }).collect(Collectors.toList());
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setData(taskVOList);
        rspData.setCount(query.count());
        return rspData;
    }

    public TableDataInfo flow(int page, int limit) {
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery()
                .orderByProcessDefinitionVersion().asc();
        List<ProcessDefinition> processDefinitions = query.listPage(page, limit);
        List<FlowVO> flowVOList = processDefinitions.stream().map(s -> {
            FlowVO flowVO = new FlowVO();
            flowVO.setId(s.getId());
            flowVO.setCategory(s.getCategory());
            flowVO.setName(s.getName());
            flowVO.setKey(s.getKey());
            flowVO.setDescription(s.getDescription());
            flowVO.setVersion(s.getVersion());
            flowVO.setDeploymentId(s.getDeploymentId());
            return flowVO;
        }).collect(Collectors.toList());
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setData(flowVOList);
        rspData.setCount(query.count());
        return rspData;
    }

    public TableDataInfo toDoTask(int page, int limit) {
        String userName = SecurityUtils.getUserName();
        TaskQuery query = taskService.createTaskQuery()
                .taskCandidateOrAssigned(userName)
                .orderByTaskCreateTime().desc();
        List<Task> tasks = query.listPage(page, limit);
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setData(tasks);
        rspData.setCount(query.count());
        return rspData;
    }

    public TableDataInfo doneTask(int page, int limit, String name, String categoryId) {
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
        List<HistoricProcessInstance> historicProcessInstances = query.listPage(page, limit);
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setData(historicProcessInstances);
        rspData.setCount(query.count());
        return rspData;
    }

    public String start(StartDTO startDTO) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(startDTO.getDeploymentId()).singleResult();
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), "abc");
        return processInstance.getProcessInstanceId();
    }
}
