package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.GroupMemberService;
import com.yangql.viewer4doc.application.GroupService;
import com.yangql.viewer4doc.application.UserService;
import com.yangql.viewer4doc.domain.GroupMember;
import com.yangql.viewer4doc.domain.GroupMemberReq;
import com.yangql.viewer4doc.domain.GroupReq;
import com.yangql.viewer4doc.domain.UserInfo;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private UserService userService;

    @ApiOperation(
            value = "그룹 멤버 추가 ",
            httpMethod = "POST",
            produces = "application/json",
            consumes = "application/json",
            protocols = "http",
            responseHeaders = {}
    )
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Not authenticated"),
            @ApiResponse(code = 403, message = "Access Token error")

    })
    @ResponseStatus(value = HttpStatus.OK)
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
            String userName = userService.getUserById(i).getName();
            GroupMember gm = GroupMember.builder()
                    .active(0L)
                    .level(1L)
                    .userId(i)
                    .userName(userName)
                    .groupId(groupId)
                    .build();
            groupMembers.add(gm);
        }
        groupMemberService.addGroupMembers(groupId,groupMembers);
        String url = "/admin/"+groupId+"/member";
        return ResponseEntity.created(new URI(url)).body("Created");
    }

    @ApiOperation(
            value = "그룹 가입 ",
            httpMethod = "POST",
            produces = "application/json",
            consumes = "application/json",
            protocols = "http",
            responseHeaders = {}
    )
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Not authenticated"),
            @ApiResponse(code = 403, message = "Access Token error")

    })
    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping("/group/{groupId}/join")
    public ResponseEntity<?> joinGroup(
            @PathVariable Long groupId,
            Authentication authentication
    ) throws URISyntaxException {
        Claims claims = (Claims)authentication.getPrincipal();
        Long userId = claims.get("userId",Long.class);
        //TODO : User 상속해서 GroupMember 만들 것
        UserInfo newUser = userService.getUserById(userId);
        String userName = "";
        if(newUser == null){
            userName = "";
            //return ResponseEntity.badRequest().body("{}");
        }
        else {
            userName = newUser.getName();
        }
        GroupMember groupMember = GroupMember.builder()
                .groupId(groupId)
                .level(1L)
                .userId(userId)
                .userName(userName)
                .active(1L)
                .build();
        groupMemberService.addGroupMember(groupId,groupMember);
        String url = "/admin/"+groupId+"/join";
        return ResponseEntity.created(new URI(url)).body("Created");
    }
    @ApiOperation(
            value = "그룹 멤버 삭제 ",
            httpMethod = "POST",
            produces = "application/json",
            consumes = "application/json",
            protocols = "http",
            responseHeaders = {}
    )
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Not authenticated"),
            @ApiResponse(code = 403, message = "Access Token error")

    })
    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping("/group/member/drop")
    public ResponseEntity<?> kickGroupMember(
            @RequestBody GroupMemberReq groupMemberReq,
            Authentication authentication
    ) {
        Claims claims = (Claims)authentication.getPrincipal();
        Long userId = claims.get("userId",Long.class);
        Long tuserId = groupMemberReq.getUserId();
        Long groupId = groupMemberReq.getGroupId();
        groupMemberService.deleteGroupMemberForKick(groupId,tuserId);
        return ResponseEntity.ok().body("Deleted");
    }
}
