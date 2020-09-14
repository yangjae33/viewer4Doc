package com.yangql.viewer4doc.application;

import com.yangql.viewer4doc.domain.UserInfo;
import com.yangql.viewer4doc.domain.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<UserInfo> getUsers() {
        List<UserInfo> userInfos = userRepository.findAll();

        return userInfos;
    }
    public void addUser(UserInfo userInfo){
        userRepository.save(userInfo);
    }
    public UserInfo getUser(Long id){
        UserInfo userInfo = userRepository.findById(id).orElse(null);
        return userInfo;
    }
}
