package io.github.yangyouwang.crud.act.factory;

import io.github.yangyouwang.crud.act.model.vo.FlowVO;
import org.activiti.engine.repository.ProcessDefinition;

/**
 * 流程静态工厂
 */
public class FlowFactory {

    /**
     * 创建流程定义VO
     * @param processDefinition 流程定义
     * @return 流程定义VO
     */
    public static FlowVO createFlow(ProcessDefinition processDefinition) {
        FlowVO flowVO = new FlowVO();
        flowVO.setId(processDefinition.getId());
        flowVO.setCategory(processDefinition.getCategory());
        flowVO.setName(processDefinition.getName());
        flowVO.setKey(processDefinition.getKey());
        flowVO.setDescription(processDefinition.getDescription());
        flowVO.setVersion(processDefinition.getVersion());
        flowVO.setDeploymentId(processDefinition.getDeploymentId());
        return flowVO;
    }
}
