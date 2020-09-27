package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.FileService;
import com.yangql.viewer4doc.application.ShareService;
import com.yangql.viewer4doc.domain.FileInfo;
import com.yangql.viewer4doc.domain.ShareFileResponse;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "User for API")
@CrossOrigin
@RestController
@RequestMapping("/api")
public class FileController {
    @Autowired
    FileService fileService;

    @Autowired
    ShareService shareService;

    @ApiOperation(
            value = "파일/공유 리스트",
            notes = "로그인 시 받은 accessToken을 header에 입력(Bearer Token)",
            httpMethod = "GET",
            produces = "application/json",
            consumes = "application/json",
            protocols = "http",
            responseHeaders = {}
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")
    })
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/files")
    public List<FileInfo> list(){
        List<FileInfo> multiFileInfo = fileService.getFiles();
        return multiFileInfo;
    }

    @ApiOperation(
            value = "파일 상세보기",
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
            @ApiResponse(code = 403, message = "Access Token error"),
            @ApiResponse(code = 404, message = "File Not Found")

    })
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/file/{id}")
    public FileInfo detail(@PathVariable("id") Long id){
        FileInfo fileInfo = fileService.getFile(id);
        return fileInfo;
    }

    @ApiOperation(
            value = "업로드한 파일 리스트",
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
    @GetMapping("/myfiles")
    public List<FileInfo> myFiles(
            Authentication authentication
    ){
        Claims claims = (Claims)authentication.getPrincipal();
        Long userId = claims.get("userId",Long.class);
        String email = claims.get("email",String.class);

        List<FileInfo> myFileInfo = fileService.getMyFiles(userId);
        return myFileInfo;
    }
    @ApiOperation(
            value = "나에게 공유된 파일 리스트",
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
    @GetMapping("/sharedfiles")
    public List<ShareFileResponse> sharedFiles(
            Authentication authentication
    ){
        Claims claims = (Claims)authentication.getPrincipal();
        Long userId = claims.get("userId",Long.class);
        String email = claims.get("email",String.class);

        List<ShareFileResponse> multiFileInfo = fileService.getSharedFiles(userId);
        return multiFileInfo;
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Not authenticated"),
            @ApiResponse(code = 403, message = "Access Token error")

    })
    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping("/delete/{fileId}")
    public ResponseEntity<?> deleteFiles(
            @PathVariable("fileId") Long fileId,
            Authentication authentication
    ){
        Claims claims = (Claims)authentication.getPrincipal();
        Long userId = claims.get("userId",Long.class);
        String email = claims.get("email",String.class);
        if(fileService.checkUserFile(userId,fileId) == false){
            return ResponseEntity.badRequest().body("Unauthorized or Not exist");
        }
        fileService.deleteAllFiles(fileId);
        shareService.deleteAllShares(fileId);

        return ResponseEntity.ok().body("DELETED");
    }
}
