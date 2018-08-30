package com.nmid.cqes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class CqesApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(CqesApplication.class, args);
        //new SpringApplicationBuilder(CqesApplication.class).web(true).run(args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(CqesApplication.class);
    }
}
