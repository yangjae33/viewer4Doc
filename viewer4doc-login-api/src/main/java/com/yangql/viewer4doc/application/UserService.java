package com.yangql.viewer4doc.application;

import com.yangql.viewer4doc.domain.UserInfo;
import com.yangql.viewer4doc.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {

    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserInfo authenticate(String email, String password) {
        UserInfo userInfo = userRepository.findByEmail(email)
                .orElseThrow(()->new EmailNotExistedException(email));

        if(!passwordEncoder.matches(password, userInfo.getPassword())){
            throw new PasswordWrongException();
        }

        return userInfo;
    }

}
