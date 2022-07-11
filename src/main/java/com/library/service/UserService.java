package com.library.service;

import com.library.domain.MyUserDetails;
import com.library.domain.User;

import com.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUsername(username);
        return user.map(MyUserDetails::new)
        .orElseThrow(() -> new UsernameNotFoundException("User with such name doesn't exist"));
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

    public void saveUser(org.springframework.security.core.userdetails.User admin) {
    }
}

