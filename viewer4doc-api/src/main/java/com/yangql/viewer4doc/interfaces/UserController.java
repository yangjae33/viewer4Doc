package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.UserService;
import com.yangql.viewer4doc.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> list(){
        List<User> users = userService.getUsers();
        return users;
    }
    @PostMapping("/user")
    public ResponseEntity<?> create(@RequestBody User resource) throws URISyntaxException {
        User user = User.builder()
                .id(1L)
                .name(resource.getName())
                .email(resource.getEmail())
                .build();
        userService.addUser(user);
        URI location = new URI("/user/"+user.getId());
        return ResponseEntity.created(location).body("{}");
    }
    @GetMapping("/user/{id}")
    public User detail(@PathVariable("id") Long id){
        User user = userService.getUser(id);
        return user;
    }
}
