package com.mastercard.coding.challenge.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	public static final Contact DEFAULT_CONTACT = new Contact("Alok Mishra", null, null);

	public static final ApiInfo DEFAULT_API_INFO = new ApiInfo("City Connect Service Api Documentation",
			"City Connect Service Api Description", "1.0.0", "urn:tos", DEFAULT_CONTACT, "Apache 2.0",
			"http://www.apache.org/licenses/LICENSE-2.0");

	private static final Set<String> DEFAULT_PRODUCES_CONSUMES = new HashSet<>(
			Arrays.asList("application/json", "application/json"));

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.mastercard.coding.challenge.controller")).build()
				.apiInfo(DEFAULT_API_INFO).produces(DEFAULT_PRODUCES_CONSUMES);
	}

}