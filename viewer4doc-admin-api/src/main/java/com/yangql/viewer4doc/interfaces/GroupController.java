package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.GroupMemberService;
import com.yangql.viewer4doc.application.GroupService;
import com.yangql.viewer4doc.domain.*;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @ApiOperation(
            value = "그룹 생성 ",
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
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/group")
    public ResponseEntity<?> createGroup(
            Authentication authentication,
            @RequestBody Map<String, String> param
    ) throws URISyntaxException {
        Claims claims = (Claims) authentication.getPrincipal();
        Long userId = claims.get("userId", Long.class);
        GroupInfo group = groupService.createGroup(userId, param.get("name"));
        GroupMember gm = GroupMember.builder()
                .active(1L)
                .userId(userId)
                .groupId(group.getId())
                .level(100L)
                .build();
        groupMemberService.addGroupMember(group.getId(), gm);

        String url = "/admin/group/" + group.getId();
        return ResponseEntity.created(new URI(url)).body("Created");
    }

    @ApiOperation(
            value = "그룹 상세보기 ",
            httpMethod = "GET",
            produces = "application/json",
            consumes = "application/json",
            protocols = "http",
            responseHeaders = {}
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Not authenticated"),
            @ApiResponse(code = 403, message = "Access Token error")

    })
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/group/{groupId}")
    public ResponseEntity<?> getGroup(
            Authentication authentication,
            @PathVariable("groupId") Long groupId
    ) {
        Claims claims = (Claims) authentication.getPrincipal();
        Long userId = claims.get("userId", Long.class);
        GroupInfo group = groupService.getGroup(groupId);

        return ResponseEntity.ok().body(group);
    }

    @ApiOperation(
            value = "그룹 전체보기 ",
            httpMethod = "GET",
            produces = "application/json",
            consumes = "application/json",
            protocols = "http",
            responseHeaders = {}
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Not authenticated"),
            @ApiResponse(code = 403, message = "Access Token error")

    })
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/group")
    public ResponseEntity<?> getGroups(
            Authentication authentication
    ) {
        Claims claims = (Claims) authentication.getPrincipal();
        Long userId = claims.get("userId", Long.class);
        List<GroupInfo> groups = groupService.getGroups();

        return ResponseEntity.ok().body(groups);
    }

    @ApiOperation(
            value = "그룹 삭제 ",
            httpMethod = "DELETE",
            produces = "application/json",
            consumes = "application/json",
            protocols = "http",
            responseHeaders = {}
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Not authenticated"),
            @ApiResponse(code = 403, message = "Access Token error")

    })
    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping("/group/{groupId}")
    public ResponseEntity<?> deleteGroup(
            @PathVariable("groupId") Long groupId,
            Authentication authentication
    ) {
        Claims claims = (Claims) authentication.getPrincipal();
        Long userId = claims.get("userId", Long.class);
        groupService.deleteGroup(groupId);
        groupMemberService.deleteGroupMembers(groupId);

        return ResponseEntity.ok().body("Deleted");
    }
}
