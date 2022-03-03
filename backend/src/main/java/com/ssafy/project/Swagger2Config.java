package com.ssafy.project;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class Swagger2Config {
	
	// .apis() : API 문서 만들어줄 범위 지정 (여기서는 com.ssafy.project의 하위 구조를 탐색
	// .paths() : API의 URL 경롤르 지정한다.
	
	// http://localhost:8080/swagger-ui.html
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.ssafy.project"))
				.paths(PathSelectors.any())
				.build();
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("공통 프로젝트")
				.version("버전 입력")
				.description("설명 입력")
				.licenseUrl("Url 입력")
				.build();
	}
	
}
