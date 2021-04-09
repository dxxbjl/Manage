package io.github.yangyouwang.core.job;

import org.springframework.stereotype.Component;

/**
 * @author yangyouwang
 * @title: TestJob
 * @projectName crud
 * @description: 测试job
 * @date 2021/4/9下午9:25
 */
@Component
public class TestJob {

    public void test() {
        System.out.println("测试");
    }
}
