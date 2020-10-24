package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.GroupMemberService;
import com.yangql.viewer4doc.domain.GroupMember;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.SecondaryTable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GroupMemberController.class)
class GroupMemberControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private GroupMemberService groupMemberService;

    @Test
    public void requestJoin() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjU0LCJlbWFpbCI6ImFkbWluIn0.FjZiIwpHdO27d2UduS6EQ3CssmEbNbSiCQ-EUNvPtKE";
        GroupMember groupMember = GroupMember.builder()
                .groupId(1L)
                .userId(54L)
                .level(1L)
                .active(1L)
                .build();
        groupMemberService.addGroupMember(1L,groupMember);
        mvc.perform(post("/admin/group/1/join")
                .header("Authorization",":Bearer"+token))
                .andExpect(status().isCreated());
    }
    @Test
    public void recvInvite(){

    }
    @Test
    public void recvRequest(){

    }
    @Test
    public void deleteMember(){
        Long tuserId = 1L;
        groupMemberService.deleteGroupMember(tuserId);
    }
}