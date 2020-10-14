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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/admin")
public class GroupMemberController {
    @Autowired
    private GroupMemberService groupMemberService;

    @PostMapping("/group/{groupId}/member")
    public ResponseEntity<?> inviteGroupMember(
            @RequestBody List<GroupMember> groupMember,
            @PathVariable Long groupId,
            Authentication authentication
    ) throws URISyntaxException {
        Claims claims = (Claims)authentication.getPrincipal();
        Long userId = claims.get("userId",Long.class);
        groupMemberService.addGroupMember(groupId,groupMember);
        String url = "/admin/"+groupId+"/member";
        return ResponseEntity.created(new URI(url)).body("Created");
    }
}
