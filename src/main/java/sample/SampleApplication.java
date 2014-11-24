package sample;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan
public class SampleApplication {
	
	private static final String MAPPER_SCAN_BASE_PACKAGE = "sample.mapper";
	private static final String TYPE_ALIASES_PACKAGE = "sample.entity";

    @Bean
    @Autowired
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
      final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
      sqlSessionFactoryBean.setDataSource(dataSource);
      sqlSessionFactoryBean.setTypeAliasesPackage(TYPE_ALIASES_PACKAGE);
      return sqlSessionFactoryBean.getObject();
    }
    
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() throws Exception {
    	final MapperScannerConfigurer msc = new MapperScannerConfigurer();
    	msc.setBasePackage(MAPPER_SCAN_BASE_PACKAGE);
    	msc.afterPropertiesSet();
    	return msc;
    }
    
    public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleApplication.class, args);
    }

}