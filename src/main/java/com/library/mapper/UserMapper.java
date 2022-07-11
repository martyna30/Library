package com.library.mapper;

import com.library.domain.User;
import com.library.domain.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class UserMapper {

    public User mapToUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getPassword(),
                userDto.getRole(),
                userDto.getUsername(),
                userDto.isActive()
        );
    }

    public UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getPassword(),
                user.getRole(),
                user.getUsername(),
                user.isActive()
        );

    }

    public List<User>mapToUsersList(final List<UserDto>usersListDTo) {
        return usersListDTo.stream()
                .map(userDto -> new User(
                        userDto.getId(),
                        userDto.getPassword(),
                        userDto.getRole(),
                        userDto.getUsername(),
                        userDto.isActive()))
                .collect(Collectors.toList());
    }

    public List<UserDto>mapToUsersDtoList(final List<User>usersList) {
        return usersList.stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getPassword(),
                        user.getRole(),
                        user.getUsername(),
                        user.isActive()))
                .collect(Collectors.toList());
    }


}


