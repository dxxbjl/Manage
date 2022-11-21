package io.github.yangyouwang.crud.act.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.yangyouwang.common.domain.TableDataInfo;
import io.github.yangyouwang.core.util.SecurityUtils;
import io.github.yangyouwang.crud.act.entity.FormData;
import io.github.yangyouwang.crud.act.model.FlowVO;
import io.github.yangyouwang.crud.act.model.StartDTO;
import io.github.yangyouwang.crud.act.model.TaskVO;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 工作流业务层
 * @author yangyouwang
 */
@Service
public class WorkFlowService {

    @Autowired
    private FormDataService formDataService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private FormService formService;

    public TableDataInfo myFlow(String name, int page, int limit) {
        String userName = SecurityUtils.getUserName();
        TaskQuery query = taskService.createTaskQuery()
                .taskAssignee(userName)
                .orderByTaskCreateTime().desc();
        if (StringUtils.isNotBlank(name)) {
            query.processDefinitionNameLike("%" + name + "%");
        }
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

    public TableDataInfo flow(String name, String key, String category, int page, int limit) {
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery()
                .orderByProcessDefinitionVersion().asc();
        if (StringUtils.isNotBlank(name)) {
            query.processDefinitionNameLike("%" + name + "%");
        }
        if (StringUtils.isNotBlank(key)) {
            query.processDefinitionKeyLike(key);
        }
        if (StringUtils.isNotBlank(category)) {
            query.processDefinitionCategory(category);
        }
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

    public void start(StartDTO startDTO) {
        String userName = SecurityUtils.getUserName();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(startDTO.getDeploymentId()).singleResult();
        if (!processDefinition.hasStartFormKey()) {
            throw new RuntimeException("流程未配置表单");
        }
        // 设置流程发起人用户信息
        Authentication.setAuthenticatedUserId(userName);
        // 发起流程
        runtimeService.startProcessInstanceById(processDefinition.getId());
    }

    public String getStartFlowForm(String deploymentId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deploymentId).singleResult();
        if (!processDefinition.hasStartFormKey()) {
            throw new RuntimeException("流程未配置表单");
        }
        String formKey = formService.getStartFormKey(processDefinition.getId());
        FormData formData = formDataService.getOne(new LambdaQueryWrapper<FormData>().eq(FormData::getFormKey, formKey));
        if (Objects.isNull(formData)) {
            throw new RuntimeException("流程表单不存在");
        }
        return formData.getFormXmlData();
    }

    public BufferedImage getFlowDiagram(String deploymentId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deploymentId).singleResult();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());
        DefaultProcessDiagramGenerator diagramGenerator = new DefaultProcessDiagramGenerator();
        return diagramGenerator.generateImage(bpmnModel, "png",
                Collections.emptyList(), Collections.emptyList(),
                "宋体", "宋体", "宋体",
                null, 0);
    }
}
