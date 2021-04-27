package io.github.yangyouwang.core.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yangyouwang
 * @title: MybatisPlusConfig
 * @projectName crud
 * @description: MybatisPlus 配置
 * @date 2021/4/12:32 PM
 */
@Configuration
@MapperScan("io.github.yangyouwang.crud.**.mapper")
public class MybatisPlusConfig {
    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
