package io.github.yangyouwang.crud.act.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.yangyouwang.crud.act.mapper.ActReModelMapper;
import io.github.yangyouwang.crud.act.entity.ActReModel;
import io.github.yangyouwang.crud.act.model.params.ActReModelAddDTO;
import io.github.yangyouwang.crud.act.model.params.ActReModelEditDTO;
import io.github.yangyouwang.crud.act.model.params.ActReModelListDTO;
import io.github.yangyouwang.crud.act.model.result.ActReModelDTO;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

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

    /**
     * 获取列表
     * @param actReModelListDTO 模型列表对象
     * @return 列表数据
     */
    public IPage list(ActReModelListDTO actReModelListDTO) {
        return this.page(new Page<>(actReModelListDTO.getPageNum(), actReModelListDTO.getPageSize()),
                new LambdaQueryWrapper<ActReModel>()
                        .like(StringUtils.isNotBlank(actReModelListDTO.getName()),ActReModel::getName , actReModelListDTO.getName())
                        .like(StringUtils.isNotBlank(actReModelListDTO.getKey()),ActReModel::getKey , actReModelListDTO.getKey()));
    }

    /**
     * 获取详情
     * @param id id
     * @return 详情
     */
    public ActReModelDTO detail(String id) {
        ActReModel actReModel = this.getById(id);
        ActReModelDTO actReModelDTO = new ActReModelDTO();
        BeanUtils.copyProperties(actReModel,actReModelDTO);
        return actReModelDTO;
    }

    /**
     * 添加模型
     * @param actReModelAddDTO 模型添加对象
     * @return 添加状态
     */
    public String add(ActReModelAddDTO actReModelAddDTO) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.put("stencilset", stencilSetNode);
            Model modelData = repositoryService.newModel();

            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, actReModelAddDTO.getName());
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            String description = StringUtils.defaultString(actReModelAddDTO.getDescription());
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
            modelData.setMetaInfo(modelObjectNode.toString());
            modelData.setName(actReModelAddDTO.getName());
            modelData.setKey(StringUtils.defaultString(actReModelAddDTO.getKey()));

            repositoryService.saveModel(modelData);
            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
            log.info(modelData.getId());
            return modelData.getId();
        } catch (Exception e) {
            log.info(e.toString());
        }
        return null;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 编辑模型
     * @param actReModelEditDTO 模型编辑对象
     * @return 编辑状态
     */
    public boolean edit(ActReModelEditDTO actReModelEditDTO) {
        ActReModel actReModel = new ActReModel();
        BeanUtil.copyProperties(actReModelEditDTO,actReModel,true, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        return this.updateById(actReModel);
    }
}
