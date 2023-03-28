package com.library.service;

import com.library.domain.ConfirmationToken;
import com.library.domain.User;
import com.library.domain.registration.RegisterCredentialsDto;
import com.library.mail.EmailService;
import com.library.repository.UserRepository;
import com.library.validation.EmailValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;


@SpringBootTest
@RunWith(SpringRunner.class)
public class RegistrationServiceTest {

    @Autowired
    ConfirmationTokenService confirmationTokenService;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Autowired
    RegistrationService registrationService;

    @Test
    public void testRegister() throws MessagingException {
        //Given
        RegisterCredentialsDto registerCredentials = new RegisterCredentialsDto("Maria", "123", "maria@gmail.com");
        User user = new User(registerCredentials.getUsername(), registerCredentials.getPassword(), registerCredentials.getEmail(), "USER");
        //When
        String token = registrationService.register(registerCredentials);
        //Then
        Assert.assertNotNull(token);
        //CleanUp

        Optional<User> userMaria = userRepository.findByEmail("maria@gmail.com");
        userService.deleteUser(userMaria.get().getId());
    }

    @Test
    public void testSaveConfirmationToken() {
        //Given
        User user = new User("Maria", "123", "maria@gmail.com", "USER");
        String token = userService.signUpUser(user);

        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token).orElseThrow(() -> new IllegalStateException("token not found"));
        LocalDateTime isAlreadyConfirmed = confirmationToken.getConfirmedAt();
        LocalDateTime expiresAt = confirmationToken.getExpiresAt();
        confirmationToken.setConfirmedAt(LocalDateTime.now());

        //When
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        user.setEnabled(true);
        userService.saveUser(user);
        boolean isEnabled = user.isEnabled();

        //Then
        Assert.assertNotNull(token);
        Assert.assertNotNull(confirmationToken);

        Assert.assertNull(isAlreadyConfirmed);
        Assert.assertFalse(expiresAt.isBefore(LocalDateTime.now()));
        Assert.assertTrue(isEnabled);

        //CleanUp
        userService.deleteUser(user.getId());
    }

    @Test
    public void testConfirmToken() {
        //Given
        User user = new User("Maria", "123", "maria@gmail.com", "USER");
        String token = userService.signUpUser(user);
        //When
        String confirmation =  registrationService.confirmToken(token);
        //Then
        Assert.assertNotNull(token);
        Assert.assertNotNull(confirmation);
        //CleanUp
        userService.deleteUser(user.getId());
    }
}
