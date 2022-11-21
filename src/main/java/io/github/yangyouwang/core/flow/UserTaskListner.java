package io.github.yangyouwang.core.flow;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
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
        System.out.println(event + " event");
    }
}
