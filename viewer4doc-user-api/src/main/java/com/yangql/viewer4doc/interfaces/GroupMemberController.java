package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.GroupMemberService;
import com.yangql.viewer4doc.domain.GroupMember;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/api")
public class GroupMemberController {
    @Autowired
    private GroupMemberService groupMemberService;


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
            GroupMember gm = GroupMember.builder()
                    .active(0L)
                    .level(1L)
                    .userId(i)
                    .groupId(groupId)
                    .build();
            groupMembers.add(gm);
        }
        groupMemberService.addGroupMembers(groupId,groupMembers);
        String url = "/api/"+groupId+"/member";
        return ResponseEntity.created(new URI(url)).body("Created");
    }
}
