package com.library.controller;

import com.library.domain.User;
import com.library.domain.UserDto;
import com.library.exception.UserNotFoundException;
import com.library.mapper.UserMapper;
import com.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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


    //@Autowired
   // jwtService jwtService;

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
    public void deleteUser(@RequestParam Long userId) {
        userService.deleteUser(userId);
    }

    @PutMapping("updateUser")
    public UserDto updateUser(@RequestBody UserDto userDto) {
        return userMapper.mapToUserDto((User) userService.saveUser(userMapper.mapToUser(userDto)));
    }

    @PostMapping("createUser")
    public void createUser(@Valid @RequestBody UserDto userDto) {
        userService.saveUser(userMapper.mapToUser(userDto));
    }

    @PostMapping("auth1")
    public String generateToken(@RequestBody User user) throws Exception {

        /*try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        UserDetails userDetails = userService.loadUserByUsername(user.getUsername());

       // String jwt = jwtService.generateToken(userDetails);
        //return  ResponseEntity.ok(jwt);
        //return jwt;
    }*/
        return null;
    }
}
