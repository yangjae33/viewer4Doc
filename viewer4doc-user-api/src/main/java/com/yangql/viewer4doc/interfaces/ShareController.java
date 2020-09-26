package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.FileService;
import com.yangql.viewer4doc.application.ShareService;
import com.yangql.viewer4doc.domain.FileInfo;
import com.yangql.viewer4doc.domain.Share;
import com.yangql.viewer4doc.domain.ShareReq;
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
@RequestMapping("/api")
public class ShareController {
    @Autowired
    FileService fileService;

    @Autowired
    ShareService shareService;
    @ApiOperation(
            value = "파일 권한 추가",
            notes = "로그인 시 받은 accessToken을 header에 입력(Bearer Token)",
            httpMethod = "POST",
            produces = "application/json",
            consumes = "application/json",
            protocols = "http",
            responseHeaders = {}
    )
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 401, message = "Not authenticated"),
            @ApiResponse(code = 403, message = "Access Token error")
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/shares")
    public ResponseEntity<?> addShare(
            @RequestBody Share share,
            Authentication authentication
    ) throws URISyntaxException {
        Claims claims = (Claims)authentication.getPrincipal();
        Long userId = claims.get("userId",Long.class);
        String email = claims.get("email",String.class);

        shareService.addShare(share);
        String url = "/api/shares/"+share.getFileId();

        return ResponseEntity.created(new URI(url)).body("CREATED");
    }
    @ApiOperation(
            value = "파일 권한 변경",
            notes = "로그인 시 받은 accessToken을 header에 입력(Bearer Token)",
            httpMethod = "PATCH",
            produces = "application/json",
            consumes = "application/json",
            protocols = "http",
            responseHeaders = {}
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Not authenticated"),
            @ApiResponse(code = 403, message = "Access Token error")
    })
    @ResponseStatus(value = HttpStatus.OK)
    @PatchMapping("/shares")
    public ResponseEntity<?> updateShare(
            @RequestBody ShareReq shareReq,
            Authentication authentication
    ) throws URISyntaxException {
        Claims claims = (Claims)authentication.getPrincipal();
        Long userId = claims.get("userId",Long.class);
        String email = claims.get("email",String.class);

        Share share = Share.builder().userId(userId).fileId(shareReq.getFileId()).level(shareReq.getLevel()).build();

        shareService.updateShare(share);
        String url = "/api/shares/"+share.getFileId();

        return ResponseEntity.created(new URI(url)).body("UPDATED");
    }
}
