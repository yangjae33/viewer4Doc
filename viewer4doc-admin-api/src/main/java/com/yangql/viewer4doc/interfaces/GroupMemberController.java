package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.GroupMemberService;
import com.yangql.viewer4doc.application.GroupService;
import com.yangql.viewer4doc.domain.GroupMember;
import com.yangql.viewer4doc.domain.GroupReq;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/admin")
public class GroupMemberController {
    @Autowired
    private GroupMemberService groupMemberService;

    @PostMapping("/group/{groupId}/member")
    public ResponseEntity<?> inviteGroupMember(
            @RequestBody List<Long> targetId,
            @PathVariable Long groupId,
            Authentication authentication
    ) throws URISyntaxException {
        Claims claims = (Claims)authentication.getPrincipal();
        Long userId = claims.get("userId",Long.class);
        List<GroupMember> groupMembers = new ArrayList<>();
        for(Long i : targetId){
            GroupMember gm = GroupMember.builder()
                    .active(0L)
                    .level(1L)
                    .userId(i)
                    .groupId(groupId)
                    .build();
            groupMembers.add(gm);
        }
        groupMemberService.addGroupMembers(groupId,groupMembers);
        String url = "/admin/"+groupId+"/member";
        return ResponseEntity.created(new URI(url)).body("Created");
    }
}
