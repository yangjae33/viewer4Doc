package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.FileService;
import com.yangql.viewer4doc.application.ShareService;
import com.yangql.viewer4doc.application.UserService;
import com.yangql.viewer4doc.domain.Share;
import com.yangql.viewer4doc.domain.ShareReq;
import com.yangql.viewer4doc.domain.ShareUserResponse;
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
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ShareController {

    @Autowired
    private FileService fileService;

    @Autowired
    private ShareService shareService;

    @Autowired
    private UserService userService;

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
            @RequestBody ShareReq shareReq,
            Authentication authentication
    ) throws URISyntaxException {
        Claims claims = (Claims)authentication.getPrincipal();
        Long userId = claims.get("userId",Long.class);
        String email = claims.get("email",String.class);

        UserInfo userInfo = userService.getUserByEmail(shareReq.getEmail());
        //TODO : userInfo가 null
        String url = "/api/shares/"+shareReq.getFileId();
        if(userInfo == null){
            return ResponseEntity.badRequest().body("User not Exist");
        }
        userId = userInfo.getId();

        Share share = Share.builder()
                .userId(userId)
                .fileId(shareReq.getFileId())
                .level(shareReq.getLevel())
                .build();

        shareService.addShare(share);
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
            @RequestBody Share share,
            Authentication authentication
    ) throws URISyntaxException {
        //TODO : ID를 직접 입력하면 안됨.
        Claims claims = (Claims)authentication.getPrincipal();
        Long userId = claims.get("userId",Long.class);
        String email = claims.get("email",String.class);

        shareService.updateShare(share);
        String url = "/api/shares/"+share.getFileId();

        return ResponseEntity.created(new URI(url)).body("UPDATED");
    }


    @ApiOperation(
            value = "파일에 대한 공유 정보",
            notes = "로그인 시 받은 accessToken을 header에 입력(Bearer Token)",
            httpMethod = "GET",
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
    @GetMapping("/shares/{fileId}")
    public ResponseEntity<List<ShareUserResponse>> shareListByFile(
            @PathVariable("fileId") Long fileId,
            Authentication authentication
    ) throws URISyntaxException {
        Claims claims = (Claims)authentication.getPrincipal();
        Long userId = claims.get("userId",Long.class);
        String email = claims.get("email",String.class);

        List<Share> shares = shareService.getShareListByFileId(fileId);
        List<ShareUserResponse> shareUserResponses = new ArrayList<>();
        for(int i = 0; i<shares.size(); i++){

            UserInfo userInfo = userService.getUserById(shares.get(i).getUserId());
            ShareUserResponse shareUserResponse = ShareUserResponse.builder()
                    .userInfo(userInfo)
                    .level(shares.get(i).getLevel())
                    .build();
            shareUserResponses.add(shareUserResponse);
        }
        String url = "/api/shares/"+fileId;

        return ResponseEntity.created(new URI(url)).body(shareUserResponses);
    }
}
