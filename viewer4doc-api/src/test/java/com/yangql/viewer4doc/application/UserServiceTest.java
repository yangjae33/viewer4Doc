package com.yangql.viewer4doc.application;

import com.yangql.viewer4doc.domain.User;
import com.yangql.viewer4doc.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class UserServiceTest {


    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockReturnRepository();
        userService = new UserService(userRepository);
    }

    private void mockReturnRepository() {
        List<User> users = new ArrayList<>();
        User user = User.builder()
                .id(1L)
                .name("jaehyuk")
                .email("a@gmail.com")
                .build();
        users.add(user);
        given(userRepository.findAll()).willReturn(users);
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
    }

    @Test
    public void addUser(){
        User user = User.builder()
                .id(1L)
                .name("jaehyuk")
                .email("a@gmail.com")
                .build();
    }
    @Test
    public void getUsers(){
        List<User> users = userService.getUsers();
        User user = users.get(0);
        assertThat(user.getName(),is("jaehyuk"));
    }
    @Test
    public void getUser(){
        User user = userService.getUser(1L);

        assertThat(user.getId(),is(1L));
    }
}