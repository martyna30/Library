package com.library.service;

import com.library.domain.User;
import com.library.domain.ConfirmationToken;
import com.library.domain.registration.RegisterCredentials;
import com.library.domain.registration.RegisterCredentialsDto;
import com.library.domain.registration.Role;
import com.library.mail.EmailSender;
import com.library.mail.EmailService;
import com.library.repository.UserRepository;
import com.library.validation.EmailValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {
    @Autowired
    ConfirmationTokenService confirmationTokenService;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private EmailService emailService;

    public String register(RegisterCredentialsDto registerCredentials) throws MessagingException {
        boolean isValidEmail = emailValidator.test(registerCredentials.getEmail());
        if(!isValidEmail) {
            throw new IllegalStateException("Email not valid");
        }
        String token = userService.signUpUser(
                new User(
                        registerCredentials.getUsername(),
                        registerCredentials.getPassword(),
                        registerCredentials.getEmail(),
                        "USER"
                )
        );
        String link = "http://localhost:8080/v1/library/register/confirm?token=" + token;
        emailService.send(registerCredentials.getEmail(), link);
        return token;
    }
    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(()->
                    new IllegalStateException("token not found"));
        if(confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiresAt = confirmationToken.getExpiresAt();

        if(expiresAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }
        confirmationToken.setConfirmedAt(LocalDateTime.now());
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        User user = userRepository.findById(confirmationToken.getUser().getId()).get();
        user.setEnabled(true);
        userService.saveUser(user);
        return "Token confirmed";
    }
}

