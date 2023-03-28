package com.library.security.config;

import com.library.security.PasswordEncoder;
import com.library.security.filter.CustomAuthorizationFilter;
import com.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@CrossOrigin
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private final UserService userService;
    public SecurityConfig(BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

        this.userService = userService;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      //User librarian = new User("Martyna",
                //bCryptPasswordEncoder.encode("123")
               // , "martyna.prawdzik@gmail.com", "ROLE_LIBRARIAN");


        //User admin = new User("Piotr",
             //   bCryptPasswordEncoder.encode("456"),"test-v5v1jt5rk@srv1.mail-tester.com",
             //   "ROLE_ADMIN");

       auth.userDetailsService(userService).passwordEncoder((bCryptPasswordEncoder));
       //userService.saveUser(librarian);
       //userService.saveUser(admin);

        auth.authenticationProvider(daoAuthenticationProvider());
    }

   @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();
        provider.setPasswordEncoder((bCryptPasswordEncoder));
        provider.setUserDetailsService(userService);
        return provider;
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("http://localhost:8080/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .allowedOrigins("*")
                        //.allowedMethods("*")
                        .allowCredentials(true);
            }
        };
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();
        http.httpBasic();//postman
        //http.headers().disable();//h2
        http.authorizeRequests().antMatchers(   "/v1/library/register/**", "/v1/library/login/**",
                        "/v1/library/token/refresh/**", ("/v1/library/logout/**")).permitAll()
                        .antMatchers(HttpMethod.OPTIONS,"/**").permitAll();
        http.authorizeRequests()
                .antMatchers("/v1/library/getBook/**").permitAll()
                .antMatchers("/v1/library/getBooks/**").permitAll()
                .antMatchers("/v1/library/getBooksTags/**").permitAll()
                .antMatchers("/v1/library/getAuthor/**").permitAll()
                .antMatchers("/v1/library/getAuthors/**").permitAll()
                .antMatchers("/v1/library/getObjectsWithSpecifiedTitleOrAuthor/**").permitAll()
                .antMatchers("/v1/library/findObjectWithSpecifiedTitleOrAuthor/**").permitAll()
                .antMatchers(HttpMethod.GET,"/v1/library/rental/getRentalsForUser/**").permitAll()
                .antMatchers("/v1/library/createObjectName/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_LIBRARIAN")
                .antMatchers(HttpMethod.PUT,"/v1/library/rental/checkoutBook/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN", "ROLE_LIBRARIAN")
                .antMatchers(HttpMethod.GET,"/v1/library/rental/getRentalsForUser/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN", "ROLE_LIBRARIAN")
                .antMatchers(HttpMethod.POST,"/v1/library/createBook/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_LIBRARIAN")
                .antMatchers(HttpMethod.POST,"/v1/library/createAuthor/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_LIBRARIAN")
                .antMatchers(HttpMethod.PUT,"/v1/library/updateBook/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_LIBRARIAN")
                .antMatchers(HttpMethod.PUT,"/v1/library/updateAuthor/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_LIBRARIAN")

                .antMatchers(HttpMethod.DELETE,"/v1/library/deleteBook/**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.DELETE,"/v1/library/deleteAuthor/**").hasAnyAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }



    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}








