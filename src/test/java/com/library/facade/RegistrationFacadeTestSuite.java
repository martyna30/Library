package com.library.facade;

import com.library.domain.User;
import com.library.domain.registration.RegisterCredentialsDto;
import com.library.repository.UserRepository;
import com.library.service.ConfirmationTokenService;
import com.library.service.RegistrationService;
import com.library.service.UserService;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.session.MockitoSessionLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.Mockito.when;
import javax.mail.MessagingException;


import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockitoSession;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class RegistrationFacadeTestSuite {
    @InjectMocks
    RegistrationFacade registrationFacade;

    @Mock
    RegistrationService registrationService;

    @Test
    public void testProcessRegistration() throws MessagingException, RegistrationProcessingException {
        //Given
        RegisterCredentialsDto registerCredentialsDto = new RegisterCredentialsDto("Maria", "123", "maria@gmail.com");
        String token = "1234";
        String confirm = "Token confirmed";

        when(registrationService.register(registerCredentialsDto)).thenReturn(token);
        when(registrationService.confirmToken(token)).thenReturn(confirm);

        //When
        String tokenValue = registrationFacade.processRegistration(registerCredentialsDto);

        //Then
        Assert.assertNotNull(token);
        Assert.assertNotNull(confirm);
        Assert.assertEquals(confirm, "Token confirmed");
        Assert.assertEquals( "1234", token);
        Assert.assertNotNull(tokenValue);
    }


}
