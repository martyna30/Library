package com.library.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.library.domain.registration.Role;
import com.library.exception.LengthOfCharacters;
import com.library.exception.NotEmptyGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    //private Long id;
    @NotBlank(groups= NotEmptyGroup.class, message = "Please enter your username")
    @Size(groups= LengthOfCharacters.class, min = 3, max = 30, message = "Length username must be between 3 and 30 characters")
    private String username;
    @NotBlank(groups= NotEmptyGroup.class, message =" Please enter your password")
    @Size(groups= LengthOfCharacters.class, min = 3, max = 30, message = "Length password must be between 3 and 30 characters")
    private String password;

}
