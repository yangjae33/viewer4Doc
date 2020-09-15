package com.yangql.viewer4doc.application;

import com.yangql.viewer4doc.domain.UserInfo;
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
import static org.mockito.BDDMockito.given;

class UserInfoServiceTest {


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
        List<UserInfo> userInfos = new ArrayList<>();
        UserInfo userInfo = UserInfo.builder()
                .id(1L)
                .name("jaehyuk")
                .email("a@gmail.com")
                .build();
        userInfos.add(userInfo);
        given(userRepository.findAll()).willReturn(userInfos);
        given(userRepository.findById(1L)).willReturn(Optional.of(userInfo));
    }

    @Test
    public void addUser(){
        UserInfo userInfo = UserInfo.builder()
                .id(1L)
                .name("jaehyuk")
                .email("a@gmail.com")
                .build();
    }
    @Test
    public void getUsers(){
        List<UserInfo> userInfos = userService.getUsers();
        UserInfo userInfo = userInfos.get(0);
        assertThat(userInfo.getName(),is("jaehyuk"));
    }
    @Test
    public void getUser(){
        UserInfo userInfo = userService.getUser(1L);

        assertThat(userInfo.getId(),is(1L));
    }
}