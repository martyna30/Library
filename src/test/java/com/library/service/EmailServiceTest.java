package com.library.service;

import com.library.domain.registration.RegisterCredentialsDto;
import com.library.mail.EmailSender;
import com.library.mail.EmailService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EmailServiceTest {
    @Autowired
    EmailService emailService;

    @Test
    public void testSend() throws MessagingException {
        //Given
        String token ="123";
        String link = "http://localhost:8080/v1/library/register/confirm?token=" + token;
        RegisterCredentialsDto registerCredentials = new RegisterCredentialsDto("Martyna", "123", "martyna.prawdzik@gmail.com");
        String to = registerCredentials.getEmail();

        //When
        emailService.send(to,link);

        //then
        Assert.assertTrue("Email has been sent", true);
    }














}
