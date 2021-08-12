package com.alkemy.java.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@PropertySource("classpath:swagger.properties")
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.securityContexts(Arrays.asList(securityContext()))
				.securitySchemes(Arrays.asList(apiKey()))
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.alkemy.java.controller"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(getApiInfo())
				;
	}


	private ApiInfo getApiInfo() {
		return new ApiInfo(
				"ONG ALKEMY API",
				"API para manjear webapp de ONG",
				"1.0",
				"https://www.alkemy.org/",
				new Contact("ONG", "https://www.alkemy.org/", "alkemy@ong.com"),
				"LICENSE",
				"LICENSE URL",
				Collections.emptyList()
				);
	}

	private ApiKey apiKey() { 
		return new ApiKey("JWT", "Authorization", "header"); 
	}

	private SecurityContext securityContext() { 
		return SecurityContext.builder().securityReferences(defaultAuth()).build(); 
	} 
	
	private List<SecurityReference> defaultAuth() { 
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything"); 
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1]; 
		authorizationScopes[0] = authorizationScope; 
		return Arrays.asList(new SecurityReference("JWT", authorizationScopes)); 
	}
}