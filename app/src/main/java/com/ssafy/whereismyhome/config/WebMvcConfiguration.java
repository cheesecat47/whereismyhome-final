package com.ssafy.whereismyhome.config;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
@MapperScan(basePackages = {"com.ssafy.whereismyhome.**.mapper"})
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final Logger logger = LoggerFactory.getLogger(WebMvcConfiguration.class);
    private final List<String> patterns = Arrays.asList("/*");

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*")
//			.allowedOrigins("http://localhost:8080", "http://localhost:8081")
                .allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(),
                        HttpMethod.DELETE.name(), HttpMethod.HEAD.name(), HttpMethod.OPTIONS.name(),
                        HttpMethod.PATCH.name())
//				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD")
//				.allowedMethods("*")
                .maxAge(1800); // 1800초 동안 preflight 결과를 캐시에 저장
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index2");
    }
}
