package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.UserService;
import com.yangql.viewer4doc.domain.UserInfo;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation(
            value = "유저 리스트",
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
    @GetMapping("/users")
    public List<UserInfo> list(){
        List<UserInfo> userInfos = userService.getUsers();
        return userInfos;
    }

    @ApiOperation(
            value = "유저 상세보기 ",
            httpMethod = "POST",
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
    @GetMapping("/users/{id}")
    public UserInfo detail(@PathVariable("id") Long id){
        UserInfo userInfo = userService.getUser(id);
        return userInfo;
    }
    @ApiOperation(
            value = "유저 삭제 ",
            httpMethod = "POST",
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
    @PostMapping("/users/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("id") Long id,
            Authentication authentication
    ){

        Claims claims = (Claims)authentication.getPrincipal();
        Long userId = claims.get("userId",Long.class);
        if(!userService.checkAdmin(userId)){
            return ResponseEntity.badRequest().body("Unauthorized");
        }
        String s = userService.deactivateUser(id);
        return ResponseEntity.ok().body(s);
    }
    @ApiOperation(
            value = "관리자 회원가입",
            httpMethod = "POST",
            produces = "application/json",
            consumes = "application/json",
            protocols = "http",
            responseHeaders = {}
    )
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Existed Email"),
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/users")
    public ResponseEntity<?> create(
            @RequestBody UserInfo resource
    ) throws URISyntaxException {

        String email = resource.getEmail();
        String name = resource.getName();
        String password = resource.getPassword();

        UserInfo userInfo = userService.registerUser(email,name,password);

        String url = "/admin/users/"+ userInfo.getId();
        return ResponseEntity.created(new URI(url)).body("Created");
    }
}
