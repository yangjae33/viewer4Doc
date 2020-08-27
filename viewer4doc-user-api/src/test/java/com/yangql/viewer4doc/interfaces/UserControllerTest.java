package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.EmailExistedException;
import com.yangql.viewer4doc.application.UserService;
import com.yangql.viewer4doc.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    private UserService userService;


    @Test
    public void create() throws Exception {
        User mockUser = User.builder()
                .id(1004L)
                .email("tester@example.com")
                .name("Tester")
                .password("test")
                .build();

        given(userService.registerUser("tester@example.com","Tester","test"))
                .willReturn(mockUser);

        mvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"tester@example.com\",\"name\":\"Tester\",\"password\":\"test\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location","/api/users/1004"));

        verify(userService).registerUser(
                eq("tester@example.com"),eq("Tester"),eq("test")
        );
    }
    @Test
    public void createWithExistedEmail() throws Exception {
        String email = "tester@example.com";
        String name = "Tester";
        String password = "test";

        given(userService.registerUser(email,name,password)).willThrow(EmailExistedException.class);

        mvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"tester@example.com\",\"name\":\"Tester\",\"password\":\"test\"}"))
                .andExpect(status().isBadRequest());

        verify(userService).registerUser(
                eq("tester@example.com"),eq("Tester"),eq("test")
        );
    }
}