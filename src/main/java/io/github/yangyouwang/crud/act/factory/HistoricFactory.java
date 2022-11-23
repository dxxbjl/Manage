package io.github.yangyouwang.crud.act.factory;

import io.github.yangyouwang.crud.act.model.HistoricTaskVO;
import org.activiti.engine.history.HistoricProcessInstance;

/**
 * 历史静态工厂类
 * @author yangyouwang
 */
public class HistoricFactory {
    /**
     * 创建历史任务VO
     * @param historicProcessInstance 历史流程实例
     * @return 历史任务VO
     */
    public static HistoricTaskVO createHistoricTask(HistoricProcessInstance historicProcessInstance) {
        HistoricTaskVO historicTaskVO = new HistoricTaskVO();
        historicTaskVO.setId(historicProcessInstance.getId());
        historicTaskVO.setName(historicProcessInstance.getName());
        historicTaskVO.setDescription(historicProcessInstance.getDescription());
        historicTaskVO.setStartTime(historicProcessInstance.getStartTime());
        historicTaskVO.setEndTime(historicProcessInstance.getEndTime());
        return historicTaskVO;
    }
}
