package com.library.facade;

import com.library.domain.registration.RegisterCredentialsDto;
import com.library.service.ConfirmationTokenService;
import com.library.service.RegistrationService;
import com.library.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

@Component
public class RegistrationFacade {
    private static Logger LOGGER = LoggerFactory.getLogger(RegistrationFacade.class);


    @Autowired
    RegistrationService registrationService;

    @Autowired
    UserService userService;


    public String processRegistration(RegisterCredentialsDto registerCredentialsDto) throws MessagingException, RegistrationProcessingException {
        String token = registrationService.register(registerCredentialsDto);

        if (token != null) {
            LOGGER.info("token is ready to confirm");
            String confirm = registrationService.confirmToken(token);
            if (confirm.equals("Token confirmed")) {
                LOGGER.info("Token has been confirmed");
                return token;
            } else {
                LOGGER.error(RegistrationProcessingException.ERR_TOKEN_NOT_CONFIRMED);
            }
        }
        LOGGER.error(RegistrationProcessingException.ERR_EMAIL_ALREADY_EXIST);
        throw new RegistrationProcessingException(RegistrationProcessingException.ERR_EMAIL_ALREADY_EXIST);

    }




}
