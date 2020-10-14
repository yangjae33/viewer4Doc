package com.yangql.viewer4doc.application;

import com.yangql.viewer4doc.domain.UserInfo;
import com.yangql.viewer4doc.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    public List<UserInfo> getUsers() {
        List<UserInfo> userInfos = userRepository.findAll();

        return userInfos;
    }
    public UserInfo getUser(Long id){
        UserInfo userInfo = userRepository.findById(id).orElse(null);
        return userInfo;
    }
    public UserInfo getUserByEmail(String email) {
        UserInfo userInfo = userRepository.findByEmail(email).orElse(null);
        return userInfo;
    }
    public String deactivateUser(Long id) {
        userRepository.deactivateUser(id);
        return "Deactivated";
    }
    public UserInfo registerUser(String email, String name, String password){
        Optional<UserInfo> existed = userRepository.findByEmail(email);
        if(existed.isPresent()){
            throw new EmailExistedException(email);
        }
        String encodedPassword = passwordEncoder.encode(password);

        UserInfo userInfo = UserInfo.builder()
                .email(email)
                .name(name)
                .password(encodedPassword)
                .level(100L)
                .build();
        return userRepository.save(userInfo);
    }
    public UserInfo getUserById(Long userId){
        UserInfo userInfo = userRepository.findById(userId).orElse(null);
        return userInfo;
    }
}
