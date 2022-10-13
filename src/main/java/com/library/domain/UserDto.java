package com.library.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.library.domain.registration.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    //private Long id;
    private String username;
    private String password;
    private String email;
    private Role role;
    //private boolean isEnabled;
}
