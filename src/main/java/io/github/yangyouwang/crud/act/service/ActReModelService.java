package io.github.yangyouwang.crud.act.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.yangyouwang.crud.act.mapper.ActReModelMapper;
import io.github.yangyouwang.crud.act.entity.ActReModel;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.activiti.editor.constants.ModelDataJsonConstants.*;

/**
 * @author yangyouwang
 * @title: ActReModelService
 * @projectName crud
 * @description: 流程模型业务层
 * @date 2021/4/10下午2:01
 */
@Service
@Slf4j
public class ActReModelService extends ServiceImpl<ActReModelMapper, ActReModel> {

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private ObjectMapper objectMapper;
    /**
     * 获取列表
     * @param actReModel 模型列表对象
     * @return 列表数据
     */
    public List<ActReModel> list(ActReModel actReModel) {
        return this.list(new LambdaQueryWrapper<ActReModel>()
                .like(StringUtils.isNotBlank(actReModel.getName()), ActReModel::getName, actReModel.getName())
                .like(StringUtils.isNotBlank(actReModel.getKey()), ActReModel::getKey, actReModel.getKey()));
    }

    /**
     * 添加模型
     * @param actReModel 模型添加对象
     * @return 添加状态
     */
    public String add(ActReModel actReModel) {
        try {
            Model modelData = repositoryService.newModel();
            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(MODEL_NAME, actReModel.getName());
            modelObjectNode.put(MODEL_REVISION, 1);
            String description = StringUtils.defaultString(actReModel.getDescription());
            modelObjectNode.put(MODEL_DESCRIPTION, description);
            modelData.setMetaInfo(modelObjectNode.toString());
            modelData.setName(actReModel.getName());
            modelData.setKey(StringUtils.defaultString(actReModel.getKey()));
            modelData.setCategory(actReModel.getCategory());
            repositoryService.saveModel(modelData);
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.put("stencilset", stencilSetNode);
            ObjectNode properties = objectMapper.createObjectNode();
            properties.put("process_id", actReModel.getKey());
            properties.put("name", actReModel.getName());
            properties.put("documentation", actReModel.getDescription());
            editorNode.put("properties",properties);
            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
            log.info(modelData.getId());
            return modelData.getId();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 部署流程模型
     */
    public String deploy(String id) {
        try {
            // 先通过modelId去查找Model记录
            Model modelData = repositoryService.getModel(id);
            ObjectNode objectNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));

            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(objectNode);
            byte[] banXmlBytes = new BpmnXMLConverter().convertToXML(model);
            // 部署
            String processName = modelData.getName() + ".bpmn20.xml";
            DeploymentBuilder db = repositoryService.createDeployment().name(modelData.getName());
            Deployment deployment = db.addString(processName, new String(banXmlBytes, "utf-8")).deploy();
            // 保存模型
            ProcessDefinition processDefinition =  repositoryService.createProcessDefinitionQuery()
                    .deploymentId(deployment.getId()).singleResult();
            modelData.setDeploymentId(deployment.getId());
            modelData.setKey(processDefinition.getKey());
            repositoryService.saveModel(modelData);
            log.info(deployment.getId());
            return deployment.getId();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void edit(ActReModel actReModel) {
        Model model = repositoryService.getModel(actReModel.getId());
        try {
            ObjectNode  modelJson = (ObjectNode) objectMapper.readTree(model.getMetaInfo());
            modelJson.put(MODEL_NAME, actReModel.getName());
            modelJson.put(MODEL_DESCRIPTION, actReModel.getDescription());
            model.setMetaInfo(modelJson.toString());
            model.setName(actReModel.getName());
            model.setKey(StringUtils.defaultString(actReModel.getKey()));
            model.setCategory(actReModel.getCategory());
            repositoryService.saveModel(model);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
