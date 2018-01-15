package io.felipepoliveira.senaikeep.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import io.felipepoliveira.senaikeep.ws.rest.v1.config.RestWebConfig;

@Configuration
@EnableWebMvc
@ComponentScan(value = {"io.felipepoliveira.senaikeep"})
@Import(value = {RestWebConfig.class})
public class WebConfig extends WebMvcConfigurationSupport{
	
	
}
