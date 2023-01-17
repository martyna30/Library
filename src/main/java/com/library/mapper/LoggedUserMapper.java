package com.library.mapper;

import com.library.domain.LoggedUserDto;
import com.library.domain.User;
import com.library.domain.UserDto;
import org.springframework.stereotype.Component;

@Component
public class LoggedUserMapper {

    public LoggedUserDto mapToLoggedUserDto(User user) {
        return new LoggedUserDto(
                user.getUsername()
                //user.getPassword()
        );
    }

    public User mapToUser(LoggedUserDto loggedUserDto) {
        return new User(
                loggedUserDto.getUsername()
                //loggedUserDto.getPassword()
        );
    }

}
