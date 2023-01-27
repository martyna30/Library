package com.library.config;

import com.library.security.filter.CustomAuthorizationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;
import java.util.List;



@Configuration///miejsce tworzenia ręcznych beanów (z innych bibliotek)lub wstrzykiwanie przez konstruktor
public class CoreConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean =  new FilterRegistrationBean();
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


}
