package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.UploadFileService;
import com.yangql.viewer4doc.domain.FileInfo;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@CrossOrigin
@RestController
public class UploadFileController {
    public static final String UPLOAD_DIR = "./uploads";

//    public final static String UPLOAD_DIR = "/Users/mac/Desktop/uploads/";

    @Autowired
    private UploadFileService uploadFileService;

    @PostMapping("/api/upload")
    public ResponseEntity<?> uploadFileWithResponseJson(
            Authentication authentication,
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request
    ) throws IOException, URISyntaxException {

        String url = "/api/upload";
        Claims claims = (Claims)authentication.getPrincipal();
        Long userId = claims.get("userId",Long.class);

        FileInfo newfile = uploadFileService.uploadFile(file,userId);

        return ResponseEntity.created(new URI(url)).body(newfile);
    }
}
