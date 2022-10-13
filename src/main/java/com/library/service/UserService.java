package com.library.service;

import com.library.controller.EmailNotFoundException;

import com.library.domain.ConfirmationToken;
import com.library.domain.MyUserDetails;
import com.library.domain.User;

import com.library.repository.UserRepository;
import com.library.validation.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    ConfirmationTokenService confirmationTokenService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUsername(username);
        return user.map(MyUserDetails::new)
                    .orElseThrow(() -> new UsernameNotFoundException("User with such name doesn't exist"));
    }
    public UserDetails loadUserByEmail(String email) throws EmailNotFoundException {
        Optional<User> users = userRepository.findByEmail(email);
        return users.map(MyUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User with such email doesn't exist"));
    }


    public String signUpUser(User user) {
        Optional<User> users = userRepository.findByEmail(user.getEmail());
        if(users.isPresent()) {
            Optional<ConfirmationToken> confirmation = confirmationTokenService.getConfirmationTokenForUser(user);
            if (confirmation.isPresent()) {
                if (confirmation.get().getConfirmedAt() != null) {
                    throw new IllegalStateException("email already taken and confirmed");
                }
            }
        }

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
      return Optional.ofNullable(user.orElseThrow(() -> new RuntimeException("User doesn't exist")));
    }

    public UserDetails saveUser(User user) {
        userRepository.save(user);
        return new MyUserDetails(user);
    }




    public void deleteUser(Long userId) {
        this.userRepository.deleteById(userId);
    }



}

