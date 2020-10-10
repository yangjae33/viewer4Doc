package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.FileService;
import com.yangql.viewer4doc.domain.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@Controller
public class FileController {
    @Autowired
    private FileService fileService;

    @GetMapping("/files")
    public ResponseEntity<?> list(){
        List<FileInfo> fileInfos = new ArrayList<>();
        fileInfos = fileService.getFiles();
        return ResponseEntity.ok().body(fileInfos);
    }

}
