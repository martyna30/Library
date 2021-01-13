package com.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
//public class LibraryApplication extends SpringBootServletInitializer {
public class LibraryApplication {
    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

    /*@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(LibraryApplication.class);
    }*/
}
