package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.EmailNotExistedException;
import com.yangql.viewer4doc.application.PasswordWrongException;
import com.yangql.viewer4doc.application.UserService;
import com.yangql.viewer4doc.domain.UserInfo;
import com.yangql.viewer4doc.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SessionController.class)
class SessionControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    private UserService userService;

    @Mock
    private JwtUtil jwtUtil;

    @Test
    public void createWithValidAttributes() throws Exception {

        Long id = 1004L;
        String name = "Tester";
        String email = "tester@example.com";
        String password = "test";

        UserInfo mockUserInfo = UserInfo.builder()
                .id(id)
                .name(name)
                .level(5L)
                .build();

        given(userService.authenticate(email,password)).willReturn(mockUserInfo);

        given(jwtUtil.createToken(id,name,null)).willReturn("header.payload.signiture");

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"tester@example.com\",\"password\":\"test\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location","/session"))
                .andExpect(content().string(containsString(".")));

        verify(userService).authenticate(eq(email),eq(password));
    }
    @Test
    public void createPublisher() throws Exception {
        Long id = 1004L;
        String name = "Tester";
        String email = "tester@example.com";
        String password = "test";

        UserInfo mockUserInfo = UserInfo.builder()
                .id(id)
                .name(name)
                .level(50L)
                .fileId(369L)
                .build();

        given(userService.authenticate(email,password)).willReturn(mockUserInfo);

        given(jwtUtil.createToken(id,name, 369L)).willReturn("header.payload.signature");

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"tester@example.com\",\"password\":\"test\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location","/session"))
//                .andExpect(content()
//                        .string(containsString("{\"accessToken\":\"header.payload.signature\"}")))
                .andExpect(content().string(containsString(".")));

        verify(userService).authenticate(eq(email),eq(password));
    }

    @Test
    public void createWithWrongPassword() throws Exception {
        String email = "tester@example.com";
        String password = "x";

        given(userService.authenticate(email,password))
                .willThrow(PasswordWrongException.class);

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"tester@example.com\",\"password\":\"x\"}"))
                .andExpect(status().isBadRequest());
        verify(userService).authenticate(eq(email),eq(password));
    }
    @Test
    public void createWithNotExistedEmail() throws Exception {
        String email = "x@example.com";
        String password = "test";
        given(userService.authenticate(email,password))
                .willThrow(EmailNotExistedException.class);

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"x@example.com\",\"password\":\"test\"}"))
                .andExpect(status().isBadRequest());

        verify(userService).authenticate(eq(email),eq(password));
    }
}