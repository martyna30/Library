package com.library.service;

import com.library.domain.ConfirmationToken;
import com.library.domain.MyUserDetails;
import com.library.domain.User;
import com.library.domain.registration.RegisterCredentialsDto;
import com.library.mail.EmailService;
import com.library.repository.UserRepository;
import com.library.validation.EmailValidator;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    ConfirmationTokenService confirmationTokenService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailValidator emailValidator;

    public UserServiceTest() {}


    @Test
    public void testLoadUserByUsername() {//OK
        //Given
        String username = "Piotr";
        Optional<User> user = userRepository.findByUsername(username);
        UserDetails userDetails = userService.loadUserByUsername(username);

        //When
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.get().getRole()));
        MyUserDetails myUserDetails = new MyUserDetails(user.get().getUsername(),
                user.get().getPassword(), authorities);

        //Then
        Assert.assertNotNull(user);
        Assert.assertNotNull(userDetails);
        Assert.assertNotNull(myUserDetails);
        //CleanUp
    }

    @Test
    public void testSignUpUser() {//OK
        //Given
        String email = "martyna.prawdzik@gmail.com";
        Optional<User> user = userRepository.findByEmail(email);
        UserDetails userDetails = userService.loadUserByEmail(email);
        ConfirmationToken confirmation = confirmationTokenService.getConfirmationTokenForUser(user.get()).get();

        //When
        String token = UUID.randomUUID().toString();
        confirmation.setToken(token);
        confirmation.setCreatedAt(LocalDateTime.now());
        confirmation.setExpiresAt(LocalDateTime.now().plusMinutes(15));
        confirmation.setUser(user.get());
        LocalDateTime isAlreadyConfirmed = confirmation.getConfirmedAt();

        confirmationTokenService.saveConfirmationToken(confirmation);

        Optional<ConfirmationToken> confirmationTokenId = confirmationTokenService.getToken(token);

        //Then
        Assert.assertNotNull(user);
        Assert.assertNotNull(userDetails);
        Assert.assertNotNull(confirmation);

        Assert.assertNull(isAlreadyConfirmed);

        Assert.assertNotNull(confirmationTokenId);
        //CleanUp
    }
    @Test
    public void testSignUp() throws MessagingException {
        //Given
        RegisterCredentialsDto registerCredentials = new RegisterCredentialsDto("Maria", "123", "maria@gmail.com");
        User user = new User(registerCredentials.getUsername(), registerCredentials.getPassword(), registerCredentials.getEmail(), "ROLE_ADMIN");
        //When
        boolean isValid = emailValidator.test(registerCredentials.getEmail());
        String token = userService.signUpUser(user);

        String link = "http://localhost:8080/v1/library/register/confirm?token=" + token;

        //Then
        emailService.send(registerCredentials.getEmail(), link);

        Assert.assertTrue(isValid);
        Assert.assertNotNull(token);
        Assert.assertNotNull(link);

        //CleanUp
        userService.deleteUser(user.getId());
    }

    @Test
    public void testSaveUser() {
        //Given
        User user = new User("Maria", "123", "maria@gmail.com", "USER");
        //When
        userService.saveUser(user);
        Optional<User> users = userRepository.findById(user.getId());
        Optional<User> user_id = userService.getUser(user.getId());

        //Then
        Assert.assertNotNull(users);
        Assert.assertNotNull(user_id.get());
        //CleanUp
        userRepository.deleteById(user_id.get().getId());
    }
    @Test
    public void testDeleteUser() {
        //Given
        User user = new User("Maria", "123", "maria@gmail.com", "USER");

        userRepository.save(user);
        Optional<User> user_id = userRepository.findById(user.getId());
        List<User>users  = userRepository.findAll();
        List<User>userslist  = userService.getAllUsers();

        //When
        userRepository.deleteById(user_id.get().getId());
        List<User>usersAfterDeleted  = userRepository.findAll();
        List<User>usersListAfterDeleted= userService.getAllUsers();

        //Then
        Assert.assertNotEquals(users.size(),usersAfterDeleted.size());
        Assert.assertNotEquals(userslist.size(),usersListAfterDeleted.size());
        //CleanUp
    }
}
