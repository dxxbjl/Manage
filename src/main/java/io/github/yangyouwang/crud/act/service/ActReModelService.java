package io.github.yangyouwang.crud.act.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.yangyouwang.crud.act.dao.ActReModelRepository;
import io.github.yangyouwang.crud.act.model.ActReModel;
import io.github.yangyouwang.crud.act.model.req.ActReModelAddReq;
import io.github.yangyouwang.crud.act.model.req.ActReModelEditReq;
import io.github.yangyouwang.crud.act.model.req.ActReModelListReq;
import io.github.yangyouwang.crud.act.model.resp.ActReModelResp;
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
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * @author yangyouwang
 * @title: ActReModelService
 * @projectName crud
 * @description: 流程模型业务层
 * @date 2021/4/10下午2:01
 */
@Service
@Slf4j
public class ActReModelService {

    @Autowired
    private ActReModelRepository actReModelRepository;

    @Autowired
    private RepositoryService repositoryService;

    /**
     * 获取列表
     * @return 列表数据
     */
    public Page<ActReModel> list(ActReModelListReq actReModelListReq) {
        Pageable pageable = PageRequest.of(actReModelListReq.getPageNum() - 1,actReModelListReq.getPageSize());
        Specification<ActReModel> query = (Specification<ActReModel>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            String name = actReModelListReq.getName();
            if(Strings.isNotBlank(name)){
                predicates.add(criteriaBuilder.like(root.get("name"),"%" + name + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return actReModelRepository.findAll(query,pageable);
    }

    /**
     * 获取详情
     * @param id id
     * @return 详情
     */
    public ActReModelResp detail(String id) {
        ActReModel actReModel = actReModelRepository.findById(id).orElse(new ActReModel());
        ActReModelResp actReModelResp = new ActReModelResp();
        BeanUtils.copyProperties(actReModel,actReModelResp);
        return actReModelResp;
    }

    /**
     * 添加模型
     * @return 添加状态
     */
    public String add(ActReModelAddReq actReModelAddReq) {
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
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, actReModelAddReq.getName());
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            String description = StringUtils.defaultString(actReModelAddReq.getDescription());
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
            modelData.setMetaInfo(modelObjectNode.toString());
            modelData.setName(actReModelAddReq.getName());
            modelData.setKey(StringUtils.defaultString(actReModelAddReq.getKey()));

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
            byte[] bpmnXmlBytes = new BpmnXMLConverter().convertToXML(model);
            // 部署
            String processName = modelData.getName() + ".bpmn20.xml";
            DeploymentBuilder db = repositoryService.createDeployment().name(modelData.getName());
            Deployment deployment = db.addString(processName, new String(bpmnXmlBytes, "utf-8")).deploy();
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
     * @return 编辑状态
     */
    public void edit(ActReModelEditReq actReModelEditReq) {
        actReModelRepository.findById(actReModelEditReq.getId()).ifPresent(actReModel -> {
            BeanUtil.copyProperties(actReModelEditReq,actReModel,true, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
            actReModelRepository.save(actReModel);
        });
    }

    /**
     * 删除模型
     * @return 删除状态
     */
    public void del(String id) {
        if (actReModelRepository.existsById(id)) {
            // 删除模型
            repositoryService.deleteModel(id);
        }
    }
}
