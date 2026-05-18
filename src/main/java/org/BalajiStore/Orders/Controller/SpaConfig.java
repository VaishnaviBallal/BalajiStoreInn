package org.BalajiStore.Orders.Controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpaConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addViewController("/{spring:[^.]*}")
                .setViewName("forward:/index.html");

        registry.addViewController("/**/{spring:[^.]*}")
                .setViewName("forward:/index.html");
    }
}