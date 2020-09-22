package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.UserService;
import com.yangql.viewer4doc.domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/users")
    public List<UserInfo> list(){
        List<UserInfo> userInfos = userService.getUsers();
        return userInfos;
    }
    @PostMapping("/users")
    public ResponseEntity<?> create(@RequestBody UserInfo resource) throws URISyntaxException {
        UserInfo userInfo = UserInfo.builder()
                .id(1L)
                .name(resource.getName())
                .email(resource.getEmail())
                .build();
        userService.addUser(userInfo);
        URI location = new URI("/users/"+ userInfo.getId());
        return ResponseEntity.created(location).body("{}");
    }
    @GetMapping("/users/{id}")
    public UserInfo detail(@PathVariable("id") Long id){
        UserInfo userInfo = userService.getUser(id);
        return userInfo;
    }
}
