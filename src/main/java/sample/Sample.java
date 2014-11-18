package sample;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@MapperScan("sample.mapper")
@EnableAutoConfiguration
public class Sample {

    @RequestMapping("/")
    String home(Model model) {
    	return home("world", model);
    }

    @RequestMapping("/{name}")
    String home(@PathVariable String name, Model model) {
    	model.addAttribute("hello", String.format("Hello %s!", name));
        return "hello";
    }
    
    @Bean
    public DataSource dataSource() {
    	final SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
    	dataSource.setDriverClass(org.h2.Driver.class);
    	dataSource.setUrl("jdbc:h2:file:/tmp/sample");
    	dataSource.setUsername("sa");
    	dataSource.setPassword("sa");
    	return dataSource;
    }

    @Bean
    @Autowired
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
      final SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
      sqlSessionFactory.setDataSource(dataSource);
      return (SqlSessionFactory) sqlSessionFactory.getObject();
    }
    
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Sample.class, args);
    }

}