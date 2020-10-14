package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.GroupService;
import com.yangql.viewer4doc.domain.GroupInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GroupController.class)
class GroupControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private GroupService groupService;
    @BeforeEach
    public void setUp() throws Exception{
        initMocks(this);
    }
    @Test
    public void create() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjU0LCJlbWFpbCI6ImFkbWluIn0.FjZiIwpHdO27d2UduS6EQ3CssmEbNbSiCQ-EUNvPtKE";

        GroupInfo group = GroupInfo.builder()
                .mId(54L)
                .name("test")
                .build();

        given(groupService.createGroup(54L,"test")).willReturn(group);

        mvc.perform(post("/admin/group")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"test\"}")
                .header("Authorization",":Bearer"+token))
                .andExpect(status().isCreated());
    }
}