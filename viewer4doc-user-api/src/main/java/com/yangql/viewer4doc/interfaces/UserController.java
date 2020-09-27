package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.UserService;
import com.yangql.viewer4doc.domain.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@Api(value = "User for API")
@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation(
            value = "사용자 회원가입",
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

        String url = "/api/users/"+ userInfo.getId();
        return ResponseEntity.created(new URI(url)).body("Created");
    }
}
