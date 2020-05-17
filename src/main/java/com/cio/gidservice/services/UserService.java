package com.cio.gidservice.services;

import com.cio.gidservice.model.Role;
import com.cio.gidservice.model.Status;
import com.cio.gidservice.model.User;
import com.cio.gidservice.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User register(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        user.setStatus(Status.ACTIVE);
        User registeredUser = userRepository.save(user);

        log.info("IN register - user " + registeredUser + " successfully registered");

        return registeredUser;
    }

    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        log.info("IN getAll - " + result.size() + " users found");
        return result;
    }

    public User findByUserName(String username) {
        User userResult = userRepository.findByUsername(username);
        log.info("IN findByUserName - user: {} found by username {}", userResult, username);
        return userResult;
    }

    public User findById(Long id) {
        User userResult = userRepository.findById(id).orElse(null);
        if(userResult == null) {
            log.warn("IN findById - no user found by id " + id);
            return null;
        }
        log.info("IN findById - user: {} found by id {}", userResult, id);
        return userResult;
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("IN delete - user with id " + id + " successfully deleted");
    }
}
