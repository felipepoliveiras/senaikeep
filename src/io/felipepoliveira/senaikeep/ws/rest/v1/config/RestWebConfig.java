package io.felipepoliveira.senaikeep.ws.rest.v1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import io.felipepoliveira.senaikeep.ws.rest.v1.interceptors.AuthenticationDelegatorInterceptor;
import io.felipepoliveira.senaikeep.ws.rest.v1.interceptors.JwtAuthorizationInterceptor;

@Configuration
public class RestWebConfig extends WebMvcConfigurationSupport{
	
	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(getAuthenticationDelegadotInterceptor())
			.addPathPatterns("/rest/1.0/**");
	}
	
	//Interceptors beans
	@Bean
	protected AuthenticationDelegatorInterceptor getAuthenticationDelegadotInterceptor() {
		return new AuthenticationDelegatorInterceptor();
	}
	
	@Bean
	protected JwtAuthorizationInterceptor getJwtAuthorizationInterceptor() {
		return new JwtAuthorizationInterceptor();
	}

}
