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

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new CustomAuthorizationFilter());
        filterRegistrationBean.setUrlPatterns(Collections.singleton("/v1/library/getBooksWithSpecifiedTitle/*"));
        filterRegistrationBean.addUrlPatterns("/v1/library/deleteBook/*");
        filterRegistrationBean.addUrlPatterns("/v1/library/updateBook/*");
        filterRegistrationBean.addUrlPatterns("/v1/library/createBook/*");
        filterRegistrationBean.addUrlPatterns("/v1/library/getBook/*");

        filterRegistrationBean.addUrlPatterns("/v1/library/token/refresh/*");

        filterRegistrationBean.addUrlPatterns("/v1/library/getAuthorsForenameWithSpecifiedCharacters");
        filterRegistrationBean.addUrlPatterns("/v1/library/getAuthorsSurnameWithSpecifiedCharacters");
        filterRegistrationBean.addUrlPatterns("/v1/library/deleteAuthor");
        filterRegistrationBean.addUrlPatterns("/v1/library/updateAuthor");
        filterRegistrationBean.addUrlPatterns("/v1/library/createAuthor");
        filterRegistrationBean.addUrlPatterns("/v1/library/findIdByName");
        filterRegistrationBean.addUrlPatterns("/v1/library/getBooksTagsWithSpecifiedCharacters");
        filterRegistrationBean.addUrlPatterns("/v1/library/getBookTag");
        filterRegistrationBean.addUrlPatterns("/v1/library/deleteBookTag");
        filterRegistrationBean.addUrlPatterns("/v1/library/updateBookTag");
        filterRegistrationBean.addUrlPatterns("/v1/library/createBookTag");
        filterRegistrationBean.addUrlPatterns("/v1/library/createObjectName");
        filterRegistrationBean.addUrlPatterns("/v1/library/rental");
        filterRegistrationBean.addUrlPatterns("/v1/library/getUser");
        filterRegistrationBean.addUrlPatterns("/v1/library/deleteUser");
        filterRegistrationBean.addUrlPatterns("/v1/library/updateUser");
        filterRegistrationBean.addUrlPatterns("/v1/library/createUser");
        return filterRegistrationBean;
    }

   @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(LibraryApplication.class);
    }



}
