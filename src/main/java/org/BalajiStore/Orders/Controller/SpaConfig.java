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

        registry.addViewController("/login")
                .setViewName("forward:/index.html");

        registry.addViewController("/qr-generator")
                .setViewName("forward:/index.html");

        registry.addViewController("/home")
                .setViewName("forward:/index.html");

        registry.addViewController("/menu")
                .setViewName("forward:/index.html");
        registry.addViewController("/dashboard")
                .setViewName("forward:/index.html");
        registry.addViewController("/items-entry")
                .setViewName("forward:/index.html");
        registry.addViewController("/daily-entry")
                .setViewName("forward:/index.html");
        registry.addViewController("/item-lookup")
                .setViewName("forward:/index.html");
        registry.addViewController("/reports")
                .setViewName("forward:/index.html");
        registry.addViewController("/bin")
                .setViewName("forward:/index.html");
        registry.addViewController("/admin-orders")
                .setViewName("forward:/index.html");
        registry.addViewController("/customer-menu/{tableNo}")
                .setViewName("forward:/index.html");
    }
}