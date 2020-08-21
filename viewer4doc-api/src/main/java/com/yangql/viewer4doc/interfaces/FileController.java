package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.FileService;
import com.yangql.viewer4doc.domain.File;
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
    public List<File> list(){
        List<File> files = fileService.getFiles();
        return files;
    }
    @GetMapping("/file/{id}")
    public File detail(@PathVariable("id") Long id){
        File file = fileService.getFile(id);
        return file;
    }
}
