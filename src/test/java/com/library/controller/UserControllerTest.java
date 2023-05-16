package com.library.controller;

import com.library.domain.*;
import com.library.exception.UserNotFoundException;
import com.library.mapper.UserMapper;
import com.library.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.InvalidClassException;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;
    @Mock
    UserMapper userMapper;
    @Mock
    AuthenticationManager authenticationManager;


    @Test
    public void testGetUsers() throws Exception {
        //Given
        List<User> users = new ArrayList<>();
        List<UserDto> usersDto = new ArrayList<>();
        usersDto.add(new UserDto());

        when(userService.getAllUsers()).thenReturn(users);
        when(userMapper.mapToUsersDtoList(users)).thenReturn(usersDto);

        //when

        List<UserDto> usersResult = userController.getAllUsers();

        //then
        Assert.assertNotNull(usersResult);
        Assert.assertEquals(1, usersResult.size());
        Assert.assertEquals(usersDto.size(), usersResult.size());
        Assert.assertTrue(usersResult.equals(usersDto));
    }


    @Test
    public void testGetUser() throws UserNotFoundException {
        //Given
        List<User> users = new ArrayList<>();
        List<UserDto> usersDto = new ArrayList<>();
        User user = new User();
        UserDto userDto = new UserDto();
        usersDto.add(new UserDto());

        //when
        when(userService.getUser(user.getId())).thenReturn(Optional.of(user));
        when(userMapper.mapToUserDto(user)).thenReturn(userDto);

        UserDto userdtoResult = userController.getUser(user.getId());

        //then
        Assert.assertNotNull(userdtoResult);
        Assert.assertTrue(userDto.equals(userdtoResult));
    }
    @Test
    public void testDeleteUser() {
        //Given
        User user = new User();
        user.setId(1L);


        //When
        ResponseEntity<Object> usersResult = userController.deleteUser(user.getId());

        //then

        assertEquals(HttpStatus.NO_CONTENT.value(), usersResult.getStatusCodeValue());

    }


    @Test
    public void testUpdateUser() throws InvalidClassException {
        //Given
        UserDto userDto = new UserDto();
        userDto.setUsername("Antoni");
        userDto.setId(1L);
        userDto.setPassword("111");
        userDto.setRole("USER");
        //when
        ResponseEntity<UserDto> usersResult = userController.updateUser(userDto);

        //then
        Assert.assertNotNull(usersResult);
        Assert.assertEquals(HttpStatus.CREATED.value(), usersResult.getStatusCodeValue());
        Assert.assertEquals("Antoni", userDto.getUsername());
    }

    @Test
    public void testCreteUser() throws InvalidClassException {
        //Given
        UserDto userDto = new UserDto();
        userDto.setUsername("Antoni");
        userDto.setId(1L);
        userDto.setPassword("111");
        userDto.setRole("USER");

        //when
        ResponseEntity<UserDto> usersResult = userController.createUser(userDto);

        //then
        Assert.assertNotNull(usersResult);
        Assert.assertEquals(HttpStatus.CREATED.value(), usersResult.getStatusCodeValue());
        Assert.assertEquals("Antoni", userDto.getUsername());
    }




}

































