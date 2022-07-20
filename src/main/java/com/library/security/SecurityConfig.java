package com.library.security;

import com.library.domain.User;
import com.library.security.filter.CustomAuthenticationFilter;
import com.library.security.filter.CustomAuthorizationFilter;
import com.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;
@CrossOrigin
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {



    @Autowired
    UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        User admin = new User(null,"Piotr",
                passwordEncoder().encode("456"),
                "ROLE_ADMIN", true);

        User librarian = new User(null,"Martyna",
                passwordEncoder().encode("123"),
                "ROLE_LIBRARIAN", true);

        User user = new User(null,"Michał",
                passwordEncoder().encode("abc"),
                "ROLE_USER", true);
        auth.userDetailsService(userService);
        //userService.saveUser(librarian);
        //userService.saveUser(user);
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
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/v1/library/login");
        //http.csrf().disable(); //postman

        //http.headers().disable();//h2
        //zmina permit autenty potem role

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/v1/library/login/**").permitAll()
                        .antMatchers(HttpMethod.OPTIONS,"/**").permitAll();
        http.authorizeRequests()
                .antMatchers("/v1/library/getBook/**").permitAll()
                .antMatchers("/v1/library/getBooks/**").permitAll()
                .antMatchers("/v1/library/getAuthors/**").permitAll()
                .antMatchers(HttpMethod.POST,"/v1/library/createBook/**").hasAnyAuthority("ROLE_LIBRARIAN")
                .antMatchers(HttpMethod.PUT,"/v1/library/updateBook/**").hasAnyAuthority("ROLE_LIBRARIAN")
                .antMatchers(HttpMethod.DELETE,"/v1/library/deleteBook/**").hasAnyAuthority("ROLE_ADMIN")
                .anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
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






