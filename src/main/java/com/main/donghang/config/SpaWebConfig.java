package com.main.donghang.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class SpaWebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/{spring:^(?!api$).*$}")
                .setViewName("forward:/index.html");

        registry.addViewController("/**/{spring:^(?!api$).*$}")
                .setViewName("forward:/index.html");

        registry.addViewController("/{spring:^(?!api$).*$}/**{spring:?!(\\.js|\\.css|\\.png|\\.jpg|\\.svg)$}")
                .setViewName("forward:/index.html");
    }
}
