package com.yangql.viewer4doc.application;

import com.yangql.viewer4doc.domain.FileInfo;
import com.yangql.viewer4doc.domain.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@Transactional
public class UploadFileService {

    public final static String UPLOAD_DIR = "/Users/mac/Desktop/uploads/";

    FileRepository fileRepository;

    @Autowired
    public UploadFileService(FileRepository fileRepository){
        this.fileRepository = fileRepository;
    }

    public FileInfo uploadFile(MultipartFile file) throws IOException {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        Path path = Paths.get(UPLOAD_DIR+fileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        FileInfo newfile = FileInfo.builder()
                .name(fileName)
                .link("*** AMAZON S3 Link ***")
                .org_name(fileName)
                .build();

        return fileRepository.save(newfile);
    }
}
