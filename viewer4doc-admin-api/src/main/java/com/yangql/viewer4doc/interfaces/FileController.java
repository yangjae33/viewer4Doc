package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.FileService;
import com.yangql.viewer4doc.domain.FileInfo;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/admin")
public class FileController {
    @Autowired
    private FileService fileService;

    @ApiOperation(
            value = "파일 리스트",
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
    @GetMapping("/files")
    public ResponseEntity<?> list(){
        List<FileInfo> fileInfos = new ArrayList<>();
        fileInfos = fileService.getFiles();
        return ResponseEntity.ok().body(fileInfos);
    }
    @ApiOperation(
            value = "파일 생성",
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
    @PostMapping("/files")
    public ResponseEntity<?> create(
            @RequestBody FileInfo fileInfo
    ) throws URISyntaxException {
        FileInfo mockFileInfo = FileInfo.builder()
                .id(fileInfo.getId())
                .name(fileInfo.getName())
                .build();
        FileInfo newFileInfo = fileService.addFile(mockFileInfo);
        String url = "/adimn/files";
        return ResponseEntity.created(new URI(url)).body("Created");
    }

    @ApiOperation(
            value = "파일 삭제 ",
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
    @PostMapping("/files/{fileId}")
    public ResponseEntity<?> deleteFiles(
            @PathVariable("fileId") Long fileId,
            Authentication authentication
    ){
        Claims claims = (Claims)authentication.getPrincipal();
        Long userId = claims.get("userId",Long.class);
//        if(!fileService.checkAdmin(userId)){
//            return ResponseEntity.badRequest().body("Unauthorized");
//        }
        fileService.deleteAllFiles(fileId,userId);
        //shareService.deleteAllShares(fileId);

        return ResponseEntity.ok().body("Deleted");
    }
}
