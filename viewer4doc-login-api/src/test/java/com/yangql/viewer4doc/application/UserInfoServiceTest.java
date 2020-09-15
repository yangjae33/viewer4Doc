package com.yangql.viewer4doc.application;

import com.yangql.viewer4doc.domain.UserInfo;
import com.yangql.viewer4doc.domain.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class UserInfoServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        userService = new UserService(userRepository,passwordEncoder);
    }
    @Test
    public void authenticateWithValidAttributes(){
        String email = "tester@example.com";
        String password = "test";
        UserInfo mockUserInfo = UserInfo.builder()
                .email(email)
                .build();

        given(userRepository.findByEmail(email)).willReturn(Optional.of(mockUserInfo));
        given(passwordEncoder.matches(any(),any())).willReturn(true);

        UserInfo userInfo = userService.authenticate(email,password);

        assertThat(userInfo.getEmail(), is(email));

    }

    @Test
    public void authenticateWithNotExistedEmail(){
        String email = "x@example.com";
        String password = "test";
        Assertions.assertThrows(EmailNotExistedException.class,()->{
            throw new EmailNotExistedException(email);
        });
    }
    @Test
    public void authenticateWithWrongPassword(){
        String email = "tester@example.com";
        String password = "x";

        Assertions.assertThrows(PasswordWrongException.class,()->{
            throw new PasswordWrongException();
        });
    }
}