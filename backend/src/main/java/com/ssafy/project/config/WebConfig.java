package com.ssafy.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.project.interceptor.JwtInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	private static final String[] EXCLUDE_PATHS = {
			"/user/login",
			"/user",
			"/swagger-ui.html"
	};
	
	// CORS 세팅
	@Override
	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "HEAD");
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowedHeaders("*")
				.exposedHeaders("auth-token");
	}
	
	@Autowired
	private JwtInterceptor jwtInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                        .addPathPatterns("/user/**", "/mainUnit/**", "/primaryUnit/**", "/subUnit/**", "/detail/**")
                        .excludePathPatterns(EXCLUDE_PATHS);
    }
}
