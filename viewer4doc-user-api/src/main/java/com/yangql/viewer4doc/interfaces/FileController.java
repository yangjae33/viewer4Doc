package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.FileService;
import com.yangql.viewer4doc.domain.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/files/{uid}")
    public List<FileInfo> userFiles(){
        List<FileInfo> multiFileInfo = fileService.getFiles();
        return multiFileInfo;
    }
}
