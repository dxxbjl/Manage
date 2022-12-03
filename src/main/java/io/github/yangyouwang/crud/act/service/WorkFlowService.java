package io.github.yangyouwang.crud.act.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.yangyouwang.common.domain.TableDataInfo;
import io.github.yangyouwang.core.util.SecurityUtils;
import io.github.yangyouwang.core.util.StringUtil;
import io.github.yangyouwang.crud.act.entity.FormData;
import io.github.yangyouwang.crud.act.factory.FlowFactory;
import io.github.yangyouwang.crud.act.factory.FormFactory;
import io.github.yangyouwang.crud.act.factory.HistoricFactory;
import io.github.yangyouwang.crud.act.factory.TaskFactory;
import io.github.yangyouwang.crud.act.model.*;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.query.Query;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
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

    @Autowired
    private FormDataService formDataService;

    public String start(StartDTO startDTO) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(startDTO.getDeploymentId()).singleResult();
        // 设置流程发起人用户信息
        String userName = SecurityUtils.getUserName();
        Authentication.setAuthenticatedUserId(userName);
        // 发起流程
        String businessKey = UUID.randomUUID().toString().replaceAll("-", "");
        // 流程变量
        Map<String, Object> variables = StringUtil.paramToMap(startDTO.getFlowForm());
        variables.put("assignee", startDTO.getAssignee());
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), businessKey , variables);
        // 设置流程实例名称
        runtimeService.setProcessInstanceName(processInstance.getId(),String.format("%s发起：%s" ,userName,processDefinition.getName()));
        return businessKey;
    }

    public FormVO getStartForm(String deploymentId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deploymentId).singleResult();
        if (processDefinition.hasStartFormKey()) {
            String formKey = formService.getStartFormKey(processDefinition.getId());
            return getFormData(formKey,null);
        }
        // 内置表单配置
        StartFormData startFormData = formService.getStartFormData(processDefinition.getId());
        List<FormProperty> formProperties = startFormData.getFormProperties();
        // 动态表单
        return getFormProperty(formProperties);
    }

    public FormVO getTaskForm(String processInstanceId) {
        Task task = taskService.createTaskQuery()
                .processInstanceId(processInstanceId).singleResult();
        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        String formKey = taskFormData.getFormKey();
        if (Strings.isNotBlank(formKey)) {
            Map<String, Object> variables = runtimeService.getVariables(task.getExecutionId());
            return getFormData(formKey,variables);
        }
        List<FormProperty> formProperties =  taskFormData.getFormProperties();
        // 动态表单
        return getFormProperty(formProperties);
    }

    /**
     * 返回自定义表单VO对象
     * @param formKey 表单KEY
     * @param variables 表单值
     * @return 自定义表单
     */
    private FormVO getFormData(String formKey, Map<String, Object> variables) {
        // 外置表单
        FormData formData = formDataService.getOne(new LambdaQueryWrapper<FormData>()
                .eq(FormData::getFormKey, formKey));
        FormVO formVO = new FormVO();
        formVO.setHasStartFormKey(true);
        FormVO.FormDataVO formDataVO = new FormVO.FormDataVO();
        formDataVO.setKey(formData.getFormXmlData());
        formDataVO.setValue(variables);
        formVO.setFormData(formDataVO);
        return formVO;
    }

    /**
     * 获取动态表单
     * @param formProperties 表单配置
     * @return 动态表单
     */
    public FormVO getFormProperty(List<FormProperty> formProperties) {
        List<FormVO.FormPropertyVO> formPropertyVOList = formProperties.stream()
                .map(FormFactory::createFormProperty).collect(Collectors.toList());
        FormVO formVO = new FormVO();
        formVO.setHasStartFormKey(false);
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
        Authentication.setAuthenticatedUserId(userName);
        String processInstanceId = completeDTO.getProcessInstanceId();
        Task task = taskService.createTaskQuery()
                .taskAssignee(userName)
                .processInstanceId(processInstanceId)
                .singleResult();
        if(task != null) {
            String taskId = task.getId();
            // 添加审批意见
            taskService.addComment(taskId,processInstanceId,completeDTO.getComment());
            // 设置下一个节点审批人
            Map<String, Object> variables = new HashMap<>();
            variables.put("assignee", completeDTO.getAssignee());
            // 完成任务
            taskService.complete(taskId,variables);
            log.info(userName + "：完成任务");
        }
    }

    public TableDataInfo getMyFlowList(int page, int limit, String name) {
        String userName = SecurityUtils.getUserName();
        TaskQuery query = taskService.createTaskQuery()
                .taskAssignee(userName)
                .orderByTaskCreateTime().desc();
        if (StringUtils.isNotBlank(name)) {
            query.processDefinitionNameLike("%" + name + "%");
        }
        List<Task> tasks = query.listPage(page - 1, limit);
        List<TaskVO> taskVOList = tasks.stream().map(s -> {
            TaskVO taskVO = TaskFactory.createTask(s);
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(s.getProcessInstanceId()).singleResult();
            taskVO.setFlowName(processInstance.getName());
            return taskVO;
        }).collect(Collectors.toList());
        return getDataTable(taskVOList,query);
    }

    public TableDataInfo getFlowList(int page, int limit, String name) {
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery()
                .orderByProcessDefinitionVersion().asc();
        if (StringUtils.isNotBlank(name)) {
            query.processDefinitionNameLike("%" + name + "%");
        }
        List<ProcessDefinition> processDefinitions = query.listPage(page - 1, limit);
        List<FlowVO> flowVOList = processDefinitions.stream()
                .map(FlowFactory::createFlow).collect(Collectors.toList());
        return getDataTable(flowVOList,query);
    }

    public TableDataInfo getToDoTaskList(int page, int limit, String name) {
        String userName = SecurityUtils.getUserName();
        TaskQuery query = taskService.createTaskQuery()
                .taskCandidateOrAssigned(userName)
                .orderByTaskCreateTime().desc();
        if (StringUtils.isNotBlank(name)) {
            query.processDefinitionNameLike("%" + name + "%");
        }
        List<Task> tasks = query.listPage(page - 1, limit);
        List<TaskVO> taskVOList = tasks.stream().map(s -> {
            TaskVO taskVO = TaskFactory.createTask(s);
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(s.getProcessInstanceId()).singleResult();
            taskVO.setFlowName(processInstance.getName());
            return taskVO;
        }).collect(Collectors.toList());
        return getDataTable(taskVOList,query);
    }

    public TableDataInfo getHistoricTaskList(int page, int limit, String name) {
        String userName = SecurityUtils.getUserName();
        HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery()
                .involvedUser(userName);
        if (StringUtils.isNotBlank(name)) {
            query.processInstanceNameLike("%" + name + "%");
        }
        query.orderByProcessInstanceStartTime().desc();
        List<HistoricProcessInstance> historicProcessInstances = query.listPage(page - 1, limit);
        List<HistoricTaskVO> historicTaskVOList = historicProcessInstances.stream()
                .map(HistoricFactory::createHistoricTask).collect(Collectors.toList());
        return getDataTable(historicTaskVOList,query);
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected TableDataInfo getDataTable(List<?> list, Query query)
    {
        TableDataInfo tableDataInfo = new TableDataInfo();
        tableDataInfo.setCode(0);
        tableDataInfo.setData(list);
        tableDataInfo.setCount(query.count());
        return tableDataInfo;
    }

    public List<HistoricVO> getHistoricListByProcessInstanceId(String processInstanceId) {
        List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByHistoricTaskInstanceEndTime().asc().list();
        return historicTaskInstances.stream().map(s -> {
            HistoricVO historicVO = new HistoricVO();
            historicVO.setName(s.getName());
            historicVO.setAssignee(s.getAssignee());
            historicVO.setEndTime(s.getEndTime());
            List<Comment> taskComments = taskService.getTaskComments(s.getId());
            List<String> comment = taskComments.stream().map(Comment::getFullMessage).collect(Collectors.toList());
            historicVO.setComment(StringUtils.join(comment,","));
            return historicVO;
        }).collect(Collectors.toList());
    }
}
