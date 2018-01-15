package io.felipepoliveira.senaikeep.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.ResourceBundleViewResolver;

import io.felipepoliveira.senaikeep.ws.rest.v1.config.RestWebConfig;

@Configuration
@EnableWebMvc
@ComponentScan(value = {"io.felipepoliveira.senaikeep"})
@Import(value = {RestWebConfig.class})
public class WebConfig extends WebMvcConfigurationSupport{
	
	@Bean
	public ViewResolver getInternalResourceViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		
		return viewResolver;
	}
	
	@Bean
	public ViewResolver resourceBundleViewResolver() {
	    ResourceBundleViewResolver bean = new ResourceBundleViewResolver();
	    bean.setBasename("views");
	    return bean;
	}
	
}
