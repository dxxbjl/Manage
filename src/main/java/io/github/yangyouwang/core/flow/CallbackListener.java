package io.github.yangyouwang.core.flow;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 回调监听
 */
public class CallbackListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        String event = execution.getEventName();
        System.out.println(event + " event");
    }
}
