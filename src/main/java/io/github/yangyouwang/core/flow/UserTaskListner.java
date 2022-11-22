package io.github.yangyouwang.core.flow;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import java.util.List;

/**
 * 用户任务监听器
 */
public class UserTaskListner implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        List<String> users = (List<String>) delegateTask.getVariable("users");
        for (String user : users) {
            delegateTask.setAssignee(user);
        }
    }
}
