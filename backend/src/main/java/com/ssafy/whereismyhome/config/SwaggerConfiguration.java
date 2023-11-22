package com.ssafy.whereismyhome.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.*;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Swagger 설정
 *
 * @see <a href="https://velog.io/@u-nij/Spring-Boot-Swagger-3.0-설정-JWT-인증-설정-Profile로-환경별-설정">Spring Boot + Swagger 3.0 설정 + JWT 인증 설정 + @Profile로 환경별 설정</a>
 * @see <a href="https://velog.io/@dirn0568/Swagger-Jwt-인증access-refresh">Swagger Jwt 인증(access, refresh)</a>
 */
@Configuration
public class SwaggerConfiguration {

//	Swagger-UI 3.x 확인
//	http://localhost:8080/{your-app-root}/swagger-ui/index.html

    private String version = "V1";
    private String title = "WhereIsMyHome API " + version;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .securityContexts(this.securityContext())
                .securitySchemes(this.apiKey())
                .consumes(getConsumeContentTypes())
                .produces(getProduceContentTypes())
                .apiInfo(apiInfo())
                .groupName(version)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ssafy.whereismyhome"))
                .paths(regex("/.*"))
                .build()
                .useDefaultResponseMessages(false);
    }

    private List<SecurityContext> securityContext() {
        List<SecurityContext> contextList = new ArrayList<>();
        contextList.add(SecurityContext.builder().securityReferences(defaultAuth("Authorization")).build());
        contextList.add(SecurityContext.builder().securityReferences(defaultAuth("Refresh")).build());
        return contextList;
    }

    private List<SecurityReference> defaultAuth(String apiKey) {
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = new AuthorizationScope("global", "accessEverything"); // 전역 범위
        return Collections.singletonList(new SecurityReference(apiKey, authorizationScopes));
    }

    private List<SecurityScheme> apiKey() {
        List<SecurityScheme> apiKeys = new ArrayList<>();
        // (1) Swagger에서 보일 이름 (2) 인증 헤더에 포함할 Key 이름 (3) 어디로 정보 넘기는지
        apiKeys.add(new ApiKey("Authorization", "Authorization", "header"));
        apiKeys.add(new ApiKey("Refresh", "Refresh", "header"));
        return apiKeys;
    }

    private Set<String> getConsumeContentTypes() {
        Set<String> consumes = new HashSet<>();
        consumes.add("application/json;charset=UTF-8");
        consumes.add("application/x-www-form-urlencoded");
        return consumes;
    }

    private Set<String> getProduceContentTypes() {
        Set<String> produces = new HashSet<>();
        produces.add("application/json;charset=UTF-8");
        return produces;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description("<h3>WhereIsMyHome API Reference for Developers<br>")
                .contact(new Contact("SSAFY", "https://edu.ssafy.com", "ssafy@ssafy.com"))
                .license("SSAFY License")
                .licenseUrl("https://www.ssafy.com/ksp/jsp/swp/etc/swpPrivacy.jsp")
                .version("1.0").build();
    }
}
