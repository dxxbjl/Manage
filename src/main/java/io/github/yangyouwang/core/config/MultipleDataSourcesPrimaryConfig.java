package io.github.yangyouwang.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
 * @title: MultipleDataSourcesPrimaryConfig
 * @projectName crud
 * @description: 多数据源
 * @date 2021/4/69:02 PM
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"io.github.yangyouwang.crud.**.dao"},
        entityManagerFactoryRef = "primaryEntityManagerFactory",
        transactionManagerRef = "primaryTransactionManager")
public class MultipleDataSourcesPrimaryConfig {

    /**
     * 配置数据源
     */
    @Bean("primaryDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource primaryDataSource(){
        return DataSourceBuilder.create().build();
    }

    /**
     * 实体管理工厂的bean
     */
    @Bean(name = "primaryEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(primaryDataSource())
                //这个是指向你的domain, entity的路径。 实体类所在的包
                .packages("io.github.yangyouwang.crud.**.model")
                .persistenceUnit("primary")
                .build();
    }

    /**
     * 配置 实体管理器
     */
    @Bean(name = "primaryEntityManager")
    @Primary
    public EntityManager primaryEntityManager(EntityManagerFactoryBuilder builder){
        return  Objects.requireNonNull(entityManagerFactory(builder).getObject()).createEntityManager();
    }

    /**
     * 配置事务管理
     */
    @Bean(name = "primaryTransactionManager")
    @Primary
    public PlatformTransactionManager primaryTransactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactory(builder).getObject()));
    }
}
