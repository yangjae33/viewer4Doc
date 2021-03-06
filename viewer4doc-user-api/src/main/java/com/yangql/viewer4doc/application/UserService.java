package com.yangql.viewer4doc.application;

import com.yangql.viewer4doc.domain.UserInfo;
import com.yangql.viewer4doc.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
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
                .level(1L)
                .build();
        return userRepository.save(userInfo);
    }
    public UserInfo getUserById(Long userId){
        UserInfo userInfo = userRepository.findById(userId).orElse(null);
        return userInfo;
    }

    public UserInfo getUserByEmail(String email) {
        UserInfo userInfo = userRepository.findByEmail(email).orElse(null);
        return userInfo;
    }
}
