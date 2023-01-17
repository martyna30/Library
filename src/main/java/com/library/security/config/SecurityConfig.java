package com.library.security.config;

import com.library.domain.User;

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

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@CrossOrigin
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private final UserService userService;

    public SecurityConfig(BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       /* User librarian = new User("Martyna",
                bCryptPasswordEncoder.encode("123"),
                "ROLE_LIBRARIAN");
        System.out.println("ROLE_LIBRARIAN");

        User admin = new User("Piotr",
                bCryptPasswordEncoder.encode("456"),
                "ROLE_ADMIN");*/

       auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
        //userService.saveUser(librarian);
        //userService.saveUser(admin);

        auth.authenticationProvider(daoAuthenticationProvider());
    }

   @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                //registry.addMapping("http://localhost:8080/v1/library/login")
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

       // CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        //customAuthenticationFilter.setFilterProcessesUrl("/v1/library/login");
        //http.headers().disable();//h2
        http.authorizeRequests().antMatchers(   "/v1/library/register/**", "/v1/library/login/**",
                        "/v1/library/token/refresh/**", "/v1/library/logout/**").permitAll()
                        .antMatchers(HttpMethod.OPTIONS,"/**").permitAll();
        http.authorizeRequests()
                .antMatchers("/v1/library/getBook/**").permitAll()
                .antMatchers("/v1/library/getBooks/**").permitAll()
                .antMatchers("/v1/library/getAuthor/**").permitAll()
                .antMatchers("/v1/library/getAuthors/**").permitAll()
                .antMatchers("/v1/library/createObjectName/**").permitAll()   //DO ZMIANY
                .antMatchers("/v1/library/getObjectsWithSpecifiedTitleOrAuthor/**").permitAll()
                .antMatchers("/v1/library/findObjectWithSpecifiedTitleOrAuthor/**").permitAll()
                .antMatchers(HttpMethod.PUT,"/v1/library/checkoutBook/**").permitAll()
                .antMatchers(HttpMethod.GET,"/v1/library/getRentalsForUser/**").permitAll()
                // .antMatchers(HttpMethod.PUT,"/v1/library/checkoutBook/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN", "ROLE_LIBRARIAN")
                .antMatchers(HttpMethod.POST,"/v1/library/createBook/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_LIBRARIAN")
                .antMatchers(HttpMethod.POST,"/v1/library/createAuthor/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_LIBRARIAN")
                .antMatchers(HttpMethod.PUT,"/v1/library/updateBook/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_LIBRARIAN")
                .antMatchers(HttpMethod.PUT,"/v1/library/updateAuthor/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_LIBRARIAN")

                .antMatchers(HttpMethod.DELETE,"/v1/library/deleteBook/**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.DELETE,"/v1/library/deleteAuthor/**").hasAnyAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

    }



    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }




}

    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       User admin = new User("Piotr",
                 passwordEncoder().encode("456"),
                 Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));

        User librarian = new User("Martyna",
                passwordEncoder().encode("123"),
        Collections.singleton(new SimpleGrantedAuthority("ROLE_LIBRARIAN")));

        auth.inMemoryAuthentication().withUser(librarian);
        auth.inMemoryAuthentication().withUser(admin);
        //do bazy
        //auth.userDetailsService(administratorService);*/
//}


    /*@Bean
    public UserDetailsManager users(DataSource dataSource) {
         UserDetails user = User.withDefaultPasswordEncoder()//hasowanie
                 .username("user")
                 .password("password")
                 .roles("USER")
                 .build();
         JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
         users.createUser(user);
         return users;
     }
    @Override
    public void configure(HttpSecurity http) throws Exception {
        /*http.authorizeRequests().regexMatchers(HttpMethod.GET, "/v1/library/getBooks/?page=(&.*|$)").authenticated()//zaloguj przez
                .and()
                 .formLogin()
                 .and()
                .oauth2Login();
                .logout.deleteCookis().
                logautSuccesfullUrl.permit all
         */



        /*http.httpBasic().and().authorizeRequests()
                .regexMatchers(HttpMethod.GET, "/v1/library/getBooks/?page=(&.*|$)").permitAll()
                //.regexMatchers(HttpMethod.GET, "/v1/library/getBooks/?page=(&.*|$)").authenticated() //dla zalogowanych
                .antMatchers(HttpMethod.POST, "/v1/library/createBook").permitAll()//.hasAnyRole("LIBRARIAN", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/v1/library/createBook").hasAnyRole("LIBRARIAN", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/v1/library/deleteBook").hasRole("ADMIN")

                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll()
                .and()
                .csrf().disable();*/

//http.csrf().disable();
// http.headers().disable();






