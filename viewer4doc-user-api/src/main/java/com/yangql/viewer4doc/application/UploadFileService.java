package com.yangql.viewer4doc.application;

import com.yangql.viewer4doc.domain.FileInfo;
import com.yangql.viewer4doc.domain.FileInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@Transactional
public class UploadFileService {

    public final static String UPLOAD_DIR = "/Users/mac/Desktop/uploads/";

    FileInfoRepository fileInfoRepository;

    @Autowired
    public UploadFileService(FileInfoRepository fileInfoRepository){
        this.fileInfoRepository = fileInfoRepository;
    }

    public FileInfo uploadFile(MultipartFile file) throws IOException {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        int pos = fileName.lastIndexOf(".");
        String ext = fileName.substring(pos+1);
        System.out.println(ext);

        if(
                !(ext.equals("docx") ||
                ext.equals("pdf") || ext.equals("xlsx") ||
                ext.equals("hwp")|| ext.equals("pptx"))
        ){
            throw new UploadWithInvalidExtensionException(fileName);
        }

        Path path = Paths.get(UPLOAD_DIR+fileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        FileInfo newfile = FileInfo.builder()
                .name(fileName)
                .link("*** Saved path ***")
                .org_name(fileName)
                .build();

        return fileInfoRepository.save(newfile);
    }
}
