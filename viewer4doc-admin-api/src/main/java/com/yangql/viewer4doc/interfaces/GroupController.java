package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.GroupMemberService;
import com.yangql.viewer4doc.application.GroupService;
import com.yangql.viewer4doc.domain.*;
import io.jsonwebtoken.Claims;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Controller
@RequestMapping("/admin")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupMemberService groupMemberService;

    @PostMapping("/group")
    public ResponseEntity<?> createGroup(
            Authentication authentication,
            @RequestBody Map<String,String> param
    ) throws URISyntaxException {
        Claims claims = (Claims)authentication.getPrincipal();
        Long userId = claims.get("userId",Long.class);
        GroupInfo group = groupService.createGroup(userId,param.get("name"));
        GroupMember gm = GroupMember.builder()
                .active(1L)
                .userId(userId)
                .groupId(group.getId())
                .level(100L)
                .build();
        groupMemberService.addGroupMember(group.getId(),gm);

        String url = "/admin/group/"+group.getId();
        return ResponseEntity.created(new URI(url)).body("Created");
    }
    @GetMapping("/group/{groupId}")
    public ResponseEntity<?> getGroup(
            Authentication authentication,
            @PathVariable("groupId") Long groupId
    ) {
        Claims claims = (Claims)authentication.getPrincipal();
        Long userId = claims.get("userId",Long.class);
        GroupInfo group = groupService.getGroup(groupId);

        return ResponseEntity.ok().body(group);
    }
    @GetMapping("/group")
    public ResponseEntity<?> getGroups(
            Authentication authentication
    ){
        Claims claims = (Claims)authentication.getPrincipal();
        Long userId = claims.get("userId",Long.class);
        List<GroupInfo> groups = groupService.getGroups();

        return ResponseEntity.ok().body(groups);
    }
    @DeleteMapping("/group/{groupId}")
    public ResponseEntity<?> deleteGroup(
            @PathVariable("groupId") Long groupId,
            Authentication authentication
    ) {
        Claims claims = (Claims)authentication.getPrincipal();
        Long userId = claims.get("userId",Long.class);
        groupService.deleteGroup(groupId);
        groupMemberService.deleteGroupMembers(groupId);

        return ResponseEntity.ok().body("Deleted");
    }
}
