package com.project.smartcity2.config;

import java.beans.PropertyVetoException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages="com.project.smartcity2")
@PropertySource("classpath:persistence-mysql.properties")
public class AppConfig implements WebMvcConfigurer{
	
	// The Variable Holding The Properties
	@Autowired
	private Environment env;
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	// The View Resolver
	@Bean
	public ViewResolver viewResolver() {
		
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("WEB-INF/view/");
		viewResolver.setSuffix(".jsp");
		
		return viewResolver;
	}
	
	// Bean For Security DataSource
	@Bean
	public DataSource securityDataSource() {
		
		//Connection Pool
		ComboPooledDataSource securityDataSource = 
									new ComboPooledDataSource();
		
		// JDBC Driver Class
		try {
			securityDataSource.setDriverClass(env.getProperty("jdbc.driver")); // Reads from the properties file
		} catch (PropertyVetoException exc) {
			throw new RuntimeException(exc);
		}
		
		// Log Connection Properties
		logger.info(">>>>jdbc.url=" + env.getProperty("jdbc.url"));
		logger.info(">>>>jdbc.user=" + env.getProperty("jdbc.user"));

		// Database Connection Properties
		securityDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
		securityDataSource.setUser(env.getProperty("jdbc.user"));
		securityDataSource.setPassword(env.getProperty("jdbc.password"));
		
		//Connection Pool Props
		securityDataSource.setInitialPoolSize(
				getIntProperty("connection.pool.initialPoolSize"));
		
		securityDataSource.setMinPoolSize(
				getIntProperty("connection.pool.minPoolSize"));
		
		securityDataSource.setMaxPoolSize(
				getIntProperty("connection.pool.maxPoolSiz"));
		
		securityDataSource.setMaxIdleTime(
				getIntProperty("connection.pool.maxIdleTime"));
		
		return securityDataSource;
		
	}
	
	// Helper method to read environment props and convert 'em to int
	private int getIntProperty(String propName) {
		String propValue = env.getProperty(propName);
		int intPropValue = Integer.parseInt(propValue);
		
		return intPropValue;
	}
	
	
	
}
