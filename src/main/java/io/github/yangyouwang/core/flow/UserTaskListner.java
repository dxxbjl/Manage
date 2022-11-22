package io.github.yangyouwang.core.flow;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * 用户任务监听器
 */
public class UserTaskListner implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        String assignee = delegateTask.getVariable("assignee").toString();
        delegateTask.setAssignee(assignee);
    }
}
