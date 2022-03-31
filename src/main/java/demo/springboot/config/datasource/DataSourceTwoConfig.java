package demo.springboot.config.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"demo.springboot.repo.datasourcetwo"},
        entityManagerFactoryRef = "dataSourceTwoEntityManagerFactory",
        transactionManagerRef = "dataSourceTwoTransactionManager"
)
public class DataSourceTwoConfig {


    @Bean(name = "dataSourceTwoProperties")
    @ConfigurationProperties("spring.datasourcetwo")
    public DataSourceProperties dataSourceProperties() { return new DataSourceProperties(); }


    @Bean(name = "dataSourceTwo")
    @ConfigurationProperties("spring.datasourcetwo.config")
    public HikariDataSource dataSource(
            @Qualifier("dataSourceTwoProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }


    @Bean(name = "dataSourceTwoEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                       @Qualifier("dataSourceTwo") DataSource dataSource){
        return builder
                .dataSource(dataSource)
                .packages("demo.springboot.entity")
                .persistenceUnit("EMPLOYEE")
                .persistenceUnit("DEPARTMENT")
                .build();
    }


    @Bean(name = "dataSourceTwoTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("dataSourceTwoEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
