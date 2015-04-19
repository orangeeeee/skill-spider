package jp.co.skill.spider.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@MapperScan("jp.co.skill.spider.dao")
public class AppConfig {

   @Bean
   public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
      SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
      sessionFactory.setDataSource(dataSource);
      sessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
      return sessionFactory.getObject();
   }
}
