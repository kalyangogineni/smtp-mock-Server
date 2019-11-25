package com.walmart.smtp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

@SpringBootApplication
@EnableSpringConfigured
public class SMTPApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SMTPApplication.class, args);
    }

}
