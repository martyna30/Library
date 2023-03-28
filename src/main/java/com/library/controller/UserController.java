package com.library.controller;

import com.library.domain.User;
import com.library.domain.UserDto;
import com.library.exception.UserNotFoundException;
import com.library.mapper.UserMapper;
import com.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/library")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    AuthenticationManager authenticationManager;


    @GetMapping("getUsers")
    public List<UserDto> getAllUsers() {
        return userMapper.mapToUsersDtoList(userService.getAllUsers());
    }

    @GetMapping("getUser")
    public UserDto getUser(@RequestParam Long userId) throws UserNotFoundException {
        return userMapper.mapToUserDto(userService.getUser(userId).orElseThrow(UserNotFoundException::new));
    }

    @DeleteMapping("deleteUser")
    public ResponseEntity<Object> deleteUser(@RequestParam Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("updateUser")
    public UserDto updateUser(@RequestBody UserDto userDto) {
        return userMapper.mapToUserDto((User) userService.saveUser(userMapper.mapToUser(userDto)));
    }

    @PostMapping("createUser")
    public UserDto createUser(@Valid @RequestBody UserDto userDto) {
        return userMapper.mapToUserDto((User) userService.saveUser(userMapper.mapToUser(userDto)));
    }


}
