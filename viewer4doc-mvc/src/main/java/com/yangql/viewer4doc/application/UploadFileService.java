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
import java.io.InputStream;
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

    public FileInfo uploadFileToPDF(MultipartFile file) throws IOException {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        int pos = fileName.lastIndexOf(".");
        String ext = fileName.substring(pos+1);
        String newFilename = fileName.substring(0,pos)+".pdf";

        if(
                !(ext.equals("docx") || ext.equals("pdf") || ext.equals("xlsx") ||
                        ext.equals("hwp")|| ext.equals("pptx"))
        ){
            throw new UploadWithInvalidExtensionException(fileName);
        }

        Path path = Paths.get(UPLOAD_DIR+fileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);


        Runtime rt = Runtime.getRuntime();
        Process p;
        String baseURL = "/Users/mac/Desktop/";
        String cmd = "python2 " + baseURL + "unoconv/unoconv.py -i utf8 -f pdf --output=" + baseURL + "converts/"
                + newFilename +" "+ baseURL + "uploads/" + fileName;
        p = rt.exec(cmd);

        BufferedReader is;
        BufferedReader es;
        es = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        is = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = is.readLine()) != null) {
            System.out.println(line);
        }
        while ((line = es.readLine()) != null) {
            System.out.println(line);
        }
        if (is != null)
            is.close();
        if (es != null)
            es.close();

        FileInfo newFile = FileInfo.builder()
                .name(newFilename)
                .link(UPLOAD_DIR+newFilename)
                .org_name(fileName)
                .build();

        return fileInfoRepository.save(newFile);
    }
}
