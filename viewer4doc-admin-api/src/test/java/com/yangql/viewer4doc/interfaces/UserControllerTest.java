package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.FileService;
import com.yangql.viewer4doc.application.UserService;
import com.yangql.viewer4doc.domain.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    public void create() throws Exception {
        UserInfo mockUserInfo = UserInfo.builder()
                .id(1004L)
                .email("tester@example.com")
                .name("Tester")
                .password("test")
                .level(100L)
                .build();

        given(userService.registerUser("tester@example.com","Tester","test"))
                .willReturn(mockUserInfo);

        mvc.perform(post("/admin/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"tester@example.com\",\"name\":\"Tester\",\"password\":\"test\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location","/admin/users/1004"));

        verify(userService).registerUser(
                eq("tester@example.com"),eq("Tester"),eq("test")
        );
    }

    @Test
    public void deactivate() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjU0LCJlbWFpbCI6ImFkbWluIn0.FjZiIwpHdO27d2UduS6EQ3CssmEbNbSiCQ-EUNvPtKE";

        given(userService.deactivateUser(1L)).willReturn("Deactivated");
        mvc.perform(post("/admin/users/1")
                .header("Authorization",":Bearer"+token))
                .andExpect(status().isOk())
                .andExpect(content().string("Deactivated"));
    }
}