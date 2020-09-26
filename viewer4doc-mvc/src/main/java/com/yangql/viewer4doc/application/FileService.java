package com.yangql.viewer4doc.application;

import com.yangql.viewer4doc.domain.FileInfo;
import com.yangql.viewer4doc.domain.FileInfoRepository;
import com.yangql.viewer4doc.interfaces.FileController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@Transactional
public class FileService {

    public final static String UPLOAD_DIR = FileController.UPLOAD_DIR;

    FileInfoRepository fileInfoRepository;

    @Autowired
    public FileService(FileInfoRepository fileInfoRepository){
        this.fileInfoRepository = fileInfoRepository;
    }

    public FileInfo uploadFile(MultipartFile file,Long userId) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        if(file.isEmpty()){
            throw new UploadFileNotExistException();
        }
        if(fileName.contains("..")){
            throw new UploadFileException();
        }
        int pos = fileName.lastIndexOf(".");
        String ext = fileName.substring(pos+1);
        String pureFileName = fileName.substring(0,pos);
        System.out.println(ext);
        if(
                !(ext.equals("docx") ||
                        ext.equals("pdf") || ext.equals("xlsx") ||
                        ext.equals("hwp")|| ext.equals("pptx"))
        ){
            throw new UploadWithInvalidExtensionException(fileName);
        }

        Path path = Paths.get(UPLOAD_DIR);
        Files.copy(file.getInputStream(), path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

        FileInfo newfile = FileInfo.builder()
                .name(pureFileName+".pdf")
                .link(Paths.get(UPLOAD_DIR+"/"+fileName).normalize().toString())
                .org_name(fileName)
                .pub_id(userId)
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

        Path path = Paths.get(UPLOAD_DIR);
        Files.copy(file.getInputStream(), path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);


        Runtime rt = Runtime.getRuntime();
        Process p;
        String baseURL = System.getProperty("user.dir");
        String cmd = "python2 " + baseURL + "/unoconv/unoconv.py -i utf8 -f pdf --output=" + baseURL + "/converts/"
                + newFilename +" "+ baseURL + "/uploads/" + fileName;
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

    public Resource loadAsResource(Long fileId) throws FileNotFoundException {
        try{
            FileInfo fileInfo = fileInfoRepository.findById(fileId).orElse(null);
            Path file = Paths.get(System.getProperty("user.dir")+"/uploads/"+fileInfo.getOrg_name());
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            }
            else{
                throw new FileNotFoundException(
                        "Could not read file:" + fileInfo.getOrg_name());
            }
        } catch (MalformedURLException | FileNotFoundException e) {
            throw new FileNotFoundException("Could not read file");
        }

    }
}