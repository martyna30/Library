package com.library.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.library.domain.MyUserDetails;
import com.library.domain.User;
import com.library.domain.UserDto;
import com.library.domain.registration.RegisterCredentialsDto;
import com.library.security.JwtToken;
import com.library.service.RegistrationService;
import com.library.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
//@SpringBootTest
//@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class SecurityControllerTest {

    @InjectMocks
    SecurityController securityController;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    RegistrationService registrationService;
   // @Mock
    @Mock
    JwtToken jwtToken;
    @Mock
    UserService userService;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;

    @Test
    public void testRegister() throws Exception  {
        //Given
        RegisterCredentialsDto registerCredentials = new RegisterCredentialsDto("Maria", "123", "maria@gmail.com");
        String register = "User has been registered";
        when(registrationService.register(registerCredentials)).thenReturn(register);

        //When
        String registerResult = securityController.register(registerCredentials);

        //Then
        Assert.assertNotNull(registerResult);
        Assert.assertEquals(register, registerResult);
        Assert.assertEquals("User has been registered", register);
        Assert.assertEquals("Maria", registerCredentials.getUsername());

    }

    @Test
    public void testConfirm() throws Exception  {
        //Given
        String token = "123";
        String confirm = "Token has been confirmed";
        when(registrationService.confirmToken(token)).thenReturn(confirm);

        //When
        String confirmResult =  securityController.confirm(token);

        //Then
        Assert.assertNotNull(confirmResult);
        Assert.assertEquals(confirm, confirmResult);
        Assert.assertEquals("Token has been confirmed", confirm);
    }
    @Test
    public void testLogin() throws Exception {
        //Given
        UserDto userDto = new UserDto(1L,"Martyna", "123", "martyna.prawdzik@gmail.com", "LIBRARIAN");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword());
        Authentication authentication = mock(Authentication.class);
        authentication.setAuthenticated(true);
        Errors errors = mock(Errors.class);

        when(authenticationManager.authenticate(token)).thenReturn(authentication);

        //When
         ResponseEntity<Object> responseEntity= securityController.login(userDto, errors, request, response);

        //Then
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(201, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testRefreshToken() throws Exception {
        //Given
        User user = new User("Martyna", "123", "martyna.prawdzik@gmail.com", "LIBRARIAN");
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        MyUserDetails myUserDetails = new MyUserDetails(user);
        Collection<SimpleGrantedAuthority> authorities = Arrays.stream(user.getRole().split(","))
               .map(SimpleGrantedAuthority::new)
               .collect(Collectors.toList());
         myUserDetails.setAuthorities(authorities);
         myUserDetails.setUsername("Jan");
         myUserDetails.setPassword("111");


         String refresh_token = JWT.create()
                .withSubject(myUserDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 5 * 60 * 1000))
                 .withClaim("role", myUserDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
        String authorization = new String("Bearer ");
        String header = new String(authorization + "" + refresh_token);

        when(jwtToken.generateToken(myUserDetails,request,response)).thenReturn(refresh_token);
        when(request.getHeader(AUTHORIZATION)).thenReturn(header);
        when(userService.loadUserByUsername(user.getUsername())).thenReturn(myUserDetails);

        Assert.assertNotNull(refresh_token);
    }
    @Test
    public void testLogout() throws Exception {
        //when
        ResponseEntity<Object> responseEntity = securityController.logout(response);

        //then
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());


    }





}
