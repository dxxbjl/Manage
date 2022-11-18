package io.github.yangyouwang.core.flow;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 结束任務监听
 */
public class StopTaskListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        String event = execution.getEventName();
        switch (event) {
            case "end" :
                System.out.println("end event");
                break;
        }
    }
}
