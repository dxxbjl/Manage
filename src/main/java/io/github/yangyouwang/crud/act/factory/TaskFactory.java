package io.github.yangyouwang.crud.act.factory;

import io.github.yangyouwang.crud.act.model.TaskVO;
import org.activiti.engine.task.Task;

/**
 * 任务静态工厂类
 * @author yangyouwang
 */
public class TaskFactory {
    /**
     * 创建任务VO对象
     * @param task 任务
     * @return 任务VO对象
     */
    public static TaskVO createTask(Task task) {
        TaskVO taskVO = new TaskVO();
        taskVO.setId(task.getId());
        taskVO.setProcessInstanceId(task.getProcessInstanceId());
        taskVO.setName(task.getName());
        taskVO.setAssignee(task.getAssignee());
        taskVO.setCreateTime(task.getCreateTime());
        return taskVO;
    }
}
