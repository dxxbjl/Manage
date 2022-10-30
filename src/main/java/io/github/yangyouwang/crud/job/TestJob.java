package io.github.yangyouwang.crud.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Description: JOB任务 <br/>
 * date: 2022/10/30 2:34<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Slf4j
@Component("jobTest")
public class TestJob {

    public void test() {
        log.info("定时任务test()：" + new Date());
    }
}
