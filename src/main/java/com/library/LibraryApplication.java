package com.library;


import com.library.security.filter.CustomAuthorizationFilter;
import com.library.validator.ValidationClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import javax.servlet.Filter;
import java.util.Base64;
import java.util.Collections;


@SpringBootApplication
public class LibraryApplication extends SpringBootServletInitializer {


//public class LibraryApplication {
    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

    /*@Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean =  new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new CustomAuthorizationFilter());
        filterRegistrationBean.setUrlPatterns(Collections.singleton("getBooksWithSpecifiedTitle"));
        //filterRegistrationBean.addUrlPatterns("deleteBook");
        //filterRegistrationBean.addUrlPatterns("updateBook");
        //filterRegistrationBean.addUrlPatterns("createBook");

        //f//ilterRegistrationBean.addUrlPatterns("/token/refresh");
        return filterRegistrationBean;
    }*/

   @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(LibraryApplication.class);
    }



}
