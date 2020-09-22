package com.yangql.viewer4doc.interfaces;


import com.yangql.viewer4doc.application.UserService;
import com.yangql.viewer4doc.domain.UserInfo;
import com.yangql.viewer4doc.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@CrossOrigin
@RestController
public class SessionController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/session")
    public ResponseEntity<SessionResponseDto> create(
            @RequestBody SessionRequestDto resource
    ) throws URISyntaxException {

        String email = resource.getEmail();
        String password = resource.getPassword();

        UserInfo userInfo = userService.authenticate(email,password);

        String accessToken = jwtUtil.createToken(
                userInfo.getId(),
                userInfo.getEmail()
                );

        String url = "/session";
        return ResponseEntity.created(new URI(url)).body(
                SessionResponseDto.builder()
                        .accessToken(accessToken)
                        .build());
    }
}

