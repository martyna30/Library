package com.library.service;

import com.library.domain.ConfirmationToken;
import com.library.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ConfirmationTokenServiceTest {
    @Autowired
    UserService userService;

    @Autowired
    ConfirmationTokenService confirmationTokenService;


    @Test
    public void saveConfirmationToken() {
        //Given
        User user = new User("Maria", "123", "maria@gmail.com", "USER");
        String token = userService.signUpUser(user);
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token).orElseThrow(()->new IllegalStateException("token not found"));

        //When
        Optional<ConfirmationToken> confirmationTokenId = confirmationTokenService.getToken(token);

        //Then
        Assert.assertNotNull(user);
        Assert.assertNotNull(token);
        Assert.assertNotNull(confirmationToken);
        Assert.assertNotNull(confirmationTokenId);

        //CleanUp
        userService.deleteUser(user.getId());
    }

    @Test
    public void testDeleteConfirmationToken() {

        //Given
        User user = new User("Maria", "123", "maria@gmail.com", "USER");
        String token = userService.signUpUser(user);
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token).orElseThrow(()->new IllegalStateException("token not found"));
        List<ConfirmationToken> confirmationTokenList = confirmationTokenService.getAll();
        //When
        userService.deleteUser(user.getId());
        List<ConfirmationToken> confirmationTokenListAfterDeleted = confirmationTokenService.getAll();

        //Then
        Assert.assertNotEquals(confirmationTokenList.size(), confirmationTokenListAfterDeleted.size());
    }
}
