package io.github.yangyouwang.crud.act.service;

import io.github.yangyouwang.common.domain.TableDataInfo;
import io.github.yangyouwang.core.util.SecurityUtils;
import io.github.yangyouwang.core.util.StringUtil;
import io.github.yangyouwang.crud.act.model.*;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 工作流业务层
 * @author yangyouwang
 */
@Service
@Slf4j
public class WorkFlowService {

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
            taskVO.setProcessInstanceId(s.getProcessInstanceId());
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(s.getProcessInstanceId()).singleResult();
            taskVO.setFlowName(processInstance.getName());
            taskVO.setName(s.getName());
            taskVO.setAssignee(s.getAssignee());
            taskVO.setCreateTime(s.getCreateTime());
            return taskVO;
        }).collect(Collectors.toList());
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setData(taskVOList);
        rspData.setCount(query.count());
        return rspData;
    }

    public TableDataInfo flow(String name, int page, int limit) {
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery()
                .orderByProcessDefinitionVersion().asc();
        if (StringUtils.isNotBlank(name)) {
            query.processDefinitionNameLike("%" + name + "%");
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

    public TableDataInfo toDoTask(int page, int limit,String name) {
        String userName = SecurityUtils.getUserName();
        TaskQuery query = taskService.createTaskQuery()
                .taskCandidateOrAssigned(userName)
                .orderByTaskCreateTime().desc();
        if (StringUtils.isNotBlank(name)) {
            query.processDefinitionNameLike("%" + name + "%");
        }
        List<Task> tasks = query.listPage(page, limit);
        List<TaskVO> taskVOList = tasks.stream().map(s -> {
            TaskVO taskVO = new TaskVO();
            taskVO.setId(s.getId());
            taskVO.setProcessInstanceId(s.getProcessInstanceId());
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(s.getProcessInstanceId()).singleResult();
            taskVO.setFlowName(processInstance.getName());
            taskVO.setName(s.getName());
            taskVO.setAssignee(s.getAssignee());
            taskVO.setCreateTime(s.getCreateTime());
            return taskVO;
        }).collect(Collectors.toList());
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setData(taskVOList);
        rspData.setCount(query.count());
        return rspData;
    }

    public TableDataInfo historicTask(int page, int limit, String name) {
        String userName = SecurityUtils.getUserName();
        HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery()
                .involvedUser(userName);
        if (StringUtils.isNotBlank(name)) {
            query.processInstanceNameLike("%" + name + "%");
        }
        query.orderByProcessInstanceStartTime().desc();
        List<HistoricProcessInstance> historicProcessInstances = query.listPage(page, limit);
        List<HistoricTaskVO> historicTaskVOList = historicProcessInstances.stream().map(s -> {
            HistoricTaskVO historicTaskVO = new HistoricTaskVO();
            historicTaskVO.setId(s.getId());
            historicTaskVO.setName(s.getName());
            historicTaskVO.setDescription(s.getDescription());
            historicTaskVO.setStartTime(s.getStartTime());
            historicTaskVO.setEndTime(s.getEndTime());
            return historicTaskVO;
        }).collect(Collectors.toList());
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setData(historicTaskVOList);
        rspData.setCount(query.count());
        return rspData;
    }

    public String start(StartDTO startDTO) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(startDTO.getDeploymentId()).singleResult();
        // 设置流程发起人用户信息
        String userName = SecurityUtils.getUserName();
        Authentication.setAuthenticatedUserId(userName);
        // 发起流程
        String businessKey = UUID.randomUUID().toString().replaceAll("-", "");
        Map variables = StringUtil.paramToMap(startDTO.getFlowForm());
        variables.put("assignee", startDTO.getAssignee());
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), businessKey , variables);
        // 设置流程实例名称
        runtimeService.setProcessInstanceName(processInstance.getId(),String.format("%s - %s" ,processDefinition.getName(),userName));
        return businessKey;
    }

    public FormVO getStartFlowForm(String deploymentId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deploymentId).singleResult();
        FormVO formVO = new FormVO();
        formVO.setHasStartFormKey(processDefinition.hasStartFormKey());
        if (processDefinition.hasStartFormKey()) {
           // 外置表单判断
        }
        // 内置表单配置
        StartFormData startFormData = formService.getStartFormData(processDefinition.getId());
        List<FormProperty> formProperties = startFormData.getFormProperties();
        if (formProperties.isEmpty()) {
            throw new RuntimeException("内置动态表单未配置");
        }
        List<FormVO.FormPropertyVO> formPropertyVOList = formProperties.stream().map(s -> {
            FormVO.FormPropertyVO formPropertyVO = new FormVO.FormPropertyVO();
            formPropertyVO.setId(s.getId());
            formPropertyVO.setName(s.getName());
            formPropertyVO.setValue(s.getValue());
            String type = s.getType().getName();
            formPropertyVO.setTypeName(type);
            if("enum".equals(type)) {
                formPropertyVO.setValues(s.getType().getInformation("values"));
            } else if("date".equals(type)){
                formPropertyVO.setDatePatterns(s.getType().getInformation("datePattern"));
            }
            return formPropertyVO;
        }).collect(Collectors.toList());
        formVO.setFormProperties(formPropertyVOList);
        return formVO;
    }

    public BufferedImage getFlowDiagram(String deploymentId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deploymentId).singleResult();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());
        DefaultProcessDiagramGenerator diagramGenerator = new DefaultProcessDiagramGenerator();
        return diagramGenerator.generateImage(bpmnModel, "png",
                Collections.emptyList(), Collections.emptyList(),
                "宋体", "宋体", "宋体",
                null, 1.0d);
    }

    public BufferedImage getCurrentFlowDiagram(String processInstanceId) throws IOException {
        // 查询流程实例ID查询流程实例
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        if (processInstance == null) {
            // 查询历史流程实例
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(processInstanceId).singleResult();
            return getFlowDiagram(historicProcessInstance.getDeploymentId());
        }
        // 根据流程定义ID获取BPMN模型对象
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
        // 生成流程图
        ProcessDiagramGenerator processDiagramGenerator = new DefaultProcessDiagramGenerator();
        InputStream inputStream = processDiagramGenerator.generateDiagram(bpmnModel, "png",
                runtimeService.getActiveActivityIds(processInstance.getId()),
                Collections.emptyList(), "宋体", "宋体", "宋体", null, 1.0d);
        return ImageIO.read(inputStream);
    }

    public void complete(CompleteDTO completeDTO) {
        String userName = SecurityUtils.getUserName();
        Task task = taskService.createTaskQuery()
                .taskId(completeDTO.getTaskId())
                .taskAssignee(userName)
                .singleResult();
        if(task != null){
            // 完成任务
            taskService.complete(task.getId());
            log.info(userName + "：完成任务");
        }
    }

    public TableDataInfo historic(int page, int limit, String processInstanceId) {
        HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByHistoricTaskInstanceEndTime().asc();
        List<HistoricTaskInstance> historicTaskInstances = query.listPage(page, limit);
        List<HistoricVO> historicVOList = historicTaskInstances.stream().map(s -> {
            HistoricVO historicVO = new HistoricVO();
            historicVO.setName(s.getName());
            historicVO.setAssignee(s.getAssignee());
            historicVO.setEndTime(s.getEndTime());
            List<Comment> taskComments = taskService.getTaskComments(s.getId());
            List<String> comment = taskComments.stream().map(Comment::getFullMessage).collect(Collectors.toList());
            historicVO.setComment(StringUtils.join(comment,","));
            return historicVO;
        }).collect(Collectors.toList());
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setData(historicVOList);
        rspData.setCount(query.count());
        return rspData;
    }
}
