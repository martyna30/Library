package com.library.mapper;

import com.library.domain.User;
import com.library.domain.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class UserMapper {

    //public User(Long id, String username,String password, String role, boolean isEnabled) {

    public User mapToUser(UserDto userDto) {
        return new User(
                userDto.getUsername(),
                userDto.getPassword(),
                userDto.getEmail(),
                userDto.getRole()

        );
    }

    public UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getRole()
        );

    }

    public List<User>mapToUsersList(final List<UserDto>usersListDTo) {
        return usersListDTo.stream()
                .map(userDto -> new User(
                        userDto.getUsername(),
                        userDto.getPassword(),
                        userDto.getEmail(),
                        userDto.getRole()))
                .collect(Collectors.toList());
    }

    public List<UserDto>mapToUsersDtoList(final List<User>usersList) {
        return usersList.stream()
                .map(user -> new UserDto(
                        user.getUsername(),
                        user.getPassword(),
                        user.getEmail(),
                        user.getRole()))
                .collect(Collectors.toList());
    }


}


