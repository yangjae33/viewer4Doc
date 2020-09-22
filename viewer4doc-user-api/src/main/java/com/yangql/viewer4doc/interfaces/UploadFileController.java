package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.UploadFileService;
import com.yangql.viewer4doc.domain.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@CrossOrigin
@RestController
public class UploadFileController {

    public final static String UPLOAD_DIR = "/Users/mac/Desktop/uploads/";

    @Autowired
    private UploadFileService uploadFileService;

    @PostMapping("/api/upload")
    public ResponseEntity<?> uploadFileWithResponseJson(
            @RequestParam("file") MultipartFile file
    ) throws IOException, URISyntaxException {
        if(file.isEmpty()){
            throw new UploadFileNotExistException();
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        String url = "/api/upload";

        FileInfo newfile = uploadFileService.uploadFile(file);

        return ResponseEntity.created(new URI(url)).body(newfile);
    }
}
