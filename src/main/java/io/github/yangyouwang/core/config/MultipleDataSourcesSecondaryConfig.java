package io.github.yangyouwang.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Objects;

/**
 * @author yangyouwang
 * @title: MultipleDataSourcesSecondaryConfig
 * @projectName crud
 * @description: 多数据源配置类. 次数据源
 * @date 2021/4/69:19 PM
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"io.github.yangyouwang.crud.**.dao"},
        entityManagerFactoryRef = "secondaryEntityManagerFactory",
        transactionManagerRef = "secondaryTransactionManager")
public class MultipleDataSourcesSecondaryConfig {

    /**
     * 配置次 数据源
     */
    @Bean("secondaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSource secondaryDataSource(){
        return DataSourceBuilder.create().build();
    }

    /**
     * 实体管理工厂的bean
     */
    @Bean(name = "secondaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(secondaryDataSource())
                //这个是指向你的domain, entity的路径
                .packages("io.github.yangyouwang.crud.**.model")
                .persistenceUnit("secondary")
                .build();
    }

    /**
     * 配置 实体管理器
     */
    @Bean(name = "secondaryEntityManager")
    public EntityManager secondaryEntityManager(EntityManagerFactoryBuilder builder){
        return Objects.requireNonNull(secondaryEntityManagerFactory(builder).getObject()).createEntityManager();
    }

    /**
     * 配置事务管理
     */
    @Bean(name = "secondaryTransactionManager")
    public PlatformTransactionManager secondaryTransactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(Objects.requireNonNull(secondaryEntityManagerFactory(builder).getObject()));
    }
}
