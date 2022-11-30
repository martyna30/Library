package com.library.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.domain.MyUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class JwtToken {


    public String generateToken(MyUserDetails userIn, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());


        long currentTimeMillis = System.currentTimeMillis();

        String access_token = JWT.create()
                .withSubject(userIn.getUsername())
                .withExpiresAt(new Date(currentTimeMillis + 5 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("role", userIn.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        String refresh_token = JWT.create()
                .withSubject(userIn.getUsername())
                .withExpiresAt(new Date(currentTimeMillis + 10 * 60 * 1000))
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


        return access_token;
    }
}


