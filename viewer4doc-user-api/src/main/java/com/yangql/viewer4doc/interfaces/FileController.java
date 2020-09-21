package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.FileService;
import com.yangql.viewer4doc.domain.FileInfo;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class FileController {
    @Autowired
    FileService fileService;

    @GetMapping("/files")
    public List<FileInfo> list(){
        List<FileInfo> multiFileInfo = fileService.getFiles();
        return multiFileInfo;
    }
    @GetMapping("/file/{id}")
    public FileInfo detail(@PathVariable("id") Long id){
        FileInfo fileInfo = fileService.getFile(id);
        return fileInfo;
    }
    @GetMapping("/files/1")
    public List<FileInfo> userFiles(
            Authentication authentication
    ){
        Claims claims = (Claims)authentication.getPrincipal();
        Long userId = claims.get("userId",Long.class);
        String email = claims.get("email",String.class);
        List<FileInfo> multiFileInfo = new ArrayList<>();
        FileInfo fileInfo = FileInfo.builder()
                .id(userId)
                .org_name(email)
                .build();
        multiFileInfo.add(fileInfo);
        fileService.addFile(fileInfo);
        return multiFileInfo;
    }
}
