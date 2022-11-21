package io.github.yangyouwang.core.flow;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户任务监听器
 */
public class UserTaskListner implements TaskListener {

    @Autowired
    private HttpServletRequest request;

    @Override
    public void notify(DelegateTask delegateTask) {
        String event = delegateTask.getEventName();
        switch (event) {
            case "create" :
                String assignee = request.getParameter("assignee");
                if (Strings.isNotBlank(assignee)) {
                    delegateTask.setAssignee(assignee);
                }
                break;
        }
    }
}
