package com.yangql.viewer4doc.application;

import com.yangql.viewer4doc.domain.User;
import com.yangql.viewer4doc.domain.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        List<User> users = userRepository.findAll();

        return users;
    }
    public void addUser(User user){
        userRepository.save(user);
    }
    public User getUser(Long id){
        User user = userRepository.findById(id).orElse(null);
        return user;
    }
}
