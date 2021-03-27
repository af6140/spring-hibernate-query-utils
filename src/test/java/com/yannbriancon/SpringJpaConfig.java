package com.yannbriancon;

import com.yannbriancon.config.NPlusOneQueriesDetectionProperties;
import com.yannbriancon.config.NPlusOneQueriesDetectionProperties.ErrorLevel;
import com.yannbriancon.interceptor.HibernateQueryInterceptor;
import java.util.Map;
import java.util.Properties;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.yannbriancon.utils.repository")
@PropertySource("classpath:application.properties")

@EnableTransactionManagement
public class SpringJpaConfig {

  @Value("${spring.datasource.driver-class-name}")
  private String driver;

  @Value("${spring.datasource.url}")
  private String url;

  @Value("${spring.datasource.username}")
  private String username;

  @Value("${spring.datasource.password}")
  private String password;

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertiesResolver() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(driver);
    dataSource.setUrl(url);
    dataSource.setUsername(username);
    dataSource.setPassword(password);

    return dataSource;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource());
    em.setPackagesToScan(new String[] {"com.yannbriancon.utils.entity"});

    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

    em.setJpaVendorAdapter(vendorAdapter);
    Properties properties = additionalProperties();
    em.setJpaProperties(additionalProperties());

    return em;
  }


  @Bean
  public PlatformTransactionManager transactionManager() {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

    return transactionManager;
  }

  @Bean
  public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
    return new PersistenceExceptionTranslationPostProcessor();
  }

  Properties additionalProperties() {
    Properties properties = new Properties();
    properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
    properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
    properties.setProperty("hibernate.show_sql", "true");
    properties.setProperty("hibernate.hbm2ddl.import_files_sql_extractor","org.hibernate.tool.hbm2ddl.MultipleLinesSqlCommandExtractor");
    properties.setProperty("hibernate.ejb.interceptor","com.yannbriancon.interceptor.HibernateQueryInterceptor");
    return properties;
  }
}
