package com.library.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;


import com.library.controller.SecurityController;
import com.library.domain.MyUserDetails;
import com.library.domain.User;
import com.library.exception.OrderChecks;
import com.library.security.LoginAuthenticationValidationException;
import com.library.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.Validator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static javax.crypto.Cipher.SECRET_KEY;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/*@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
   @Autowired
   SecurityController securityController;

    private final AuthenticationManager authenticationManager;
    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager= authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication( HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            log.info("username:", username);
            log.info("password:", password);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            return authenticationManager.authenticate(authenticationToken);
        }
        catch (BadCredentialsException e) {
            System.out.println("false");
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        MyUserDetails userIn = (MyUserDetails) authentication.getPrincipal();


       // String password = request.getParameter("password");

        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());


        long currentTimeMillis = System.currentTimeMillis();

        String access_token = JWT.create()
                .withSubject(userIn.getUsername())
                .withExpiresAt(new Date(currentTimeMillis + 2 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("role", userIn.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        String refresh_token = JWT.create()
                .withSubject(userIn.getUsername())
                .withExpiresAt(new Date(currentTimeMillis + 5 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("role", userIn.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        response.setHeader("access_token", access_token);
        response.setHeader("refresh_token", refresh_token);

        Map<String, String> tokens =  new HashMap<>();
        tokens.put("access_token",access_token);
        tokens.put("refresh_token",refresh_token);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);

    }
}*/




