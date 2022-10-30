package io.github.yangyouwang.core.job;

import io.github.yangyouwang.core.util.JobInvokeUtil;
import io.github.yangyouwang.crud.qrtz.entity.QrtzJob;
import org.quartz.JobExecutionContext;

/**
 * Description: 执行Job任务<br/>
 * date: 2022/10/30 1:46<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
public class QuartzJobExecution extends AbstractQuartzJob {

    @Override
    protected void doExecute(JobExecutionContext context, QrtzJob task) throws Exception {
        JobInvokeUtil.invokeMethod(task);
    }
}
