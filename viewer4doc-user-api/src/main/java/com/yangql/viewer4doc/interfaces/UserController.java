package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.UserService;
import com.yangql.viewer4doc.domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<?> create(
            @RequestBody UserInfo resource
    ) throws URISyntaxException {

        String email = resource.getEmail();
        String name = resource.getName();
        String password = resource.getPassword();

        UserInfo userInfo = userService.registerUser(email,name,password);

        String url = "/api/users/"+ userInfo.getId();
        return ResponseEntity.created(new URI(url)).body("{}");

    }
}
