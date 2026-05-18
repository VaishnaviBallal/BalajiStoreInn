package org.BalajiStore.Orders.Controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpaConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addViewController("/")
                .setViewName("forward:/index.html");

        registry.addViewController("/customer-menu")
                .setViewName("forward:/index.html");

        registry.addViewController("/admin-orders")
                .setViewName("forward:/index.html");

        registry.addViewController("/qr-generator")
                .setViewName("forward:/index.html");

        registry.addViewController("/home")
                .setViewName("forward:/index.html");
    }
}