package com.greendays.greendays.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@Configuration
public class GreendaysWebApplicationInitializer implements WebApplicationInitializer {

    //TODO: Change this when deploying
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.setInitParameter(
                "spring.profiles.active", "local");
    }
}
