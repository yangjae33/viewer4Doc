package com.yangql.viewer4doc.application;

import com.yangql.viewer4doc.domain.FileInfo;
import com.yangql.viewer4doc.domain.FileInfoRepository;
import com.yangql.viewer4doc.domain.Share;
import com.yangql.viewer4doc.interfaces.FileController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

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
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        fileName = fileName.replaceAll(" ","_");
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

        FileInfo newFile = FileInfo.builder()
                .name(pureFileName+".pdf")
                .link(Paths.get(UPLOAD_DIR+"/"+fileName).normalize().toString())
                .orgName(fileName)
                .pubId(userId)
                .createdTimeAt(ZonedDateTime.now(ZoneId.of("Asia/Seoul")))
                .fileSize(Math.round(file.getSize()*0.0009765625))//KB
                .level(1L)
                .build();
        Share share = Share.builder()
                .fileId(newFile.getId())
                .userId(newFile.getPubId())
                .level(0L)
                .build();

        return fileInfoRepository.save(newFile);
    }

    public FileInfo uploadFileToPDF(MultipartFile file,Long userId) throws IOException {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        fileName = fileName.replaceAll(" ","_");
        int pos = fileName.lastIndexOf(".");
        String ext = fileName.substring(pos+1);
        String pureFileName = fileName.substring(0,pos);
        String newFilename = fileName.substring(0,pos)+".pdf";

        if(
                !(ext.equals("docx") || ext.equals("pdf") || ext.equals("xlsx") ||
                        ext.equals("hwp")|| ext.equals("pptx"))
        ){
            throw new UploadWithInvalidExtensionException(fileName);
        }
        if(ext.equals("hwp")){

            Path path = Paths.get(UPLOAD_DIR);
            Files.copy(file.getInputStream(), path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

            Runtime rt = Runtime.getRuntime();
            Process p;
            String baseURL = System.getProperty("user.dir");
            String cmd = "python2 "+baseURL+"/hwptopdf/hwp5html.py --output "+baseURL+"/hwptopdf/temp/ "+baseURL+"/uploads/"
                    + fileName;
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

//            p = rt.exec("cp -r /usr/share/tomcat/" + pureFileName + " /usr/share/tomcat/webapps/viewer/");
//            InputStream in7 = p.getInputStream();
//            InputStreamReader isr7 = new InputStreamReader(in7);
//            System.out.println(isr7.getEncoding());
//            BufferedReader br7 = new BufferedReader(isr7);
//            String line7;
//            while ((line7 = br7.readLine()) != null) {
//                System.out.println(line7);
//            }
//            in7.close();

            p = rt.exec(
                    "wkhtmltopdf "+ baseURL+"/hwptopdf/temp"+
                            "/index.xhtml " +baseURL + "/converts/" + pureFileName
                            + ".pdf");
            InputStream in4 = p.getInputStream();
            InputStreamReader isr4 = new InputStreamReader(in4);
            System.out.println(isr4.getEncoding());
            BufferedReader br4 = new BufferedReader(isr4);
            String line4;
            while ((line4 = br4.readLine()) != null) {
                System.out.println(line4);
            }
            in4.close();

            FileInfo newFile = FileInfo.builder()
                    .name(newFilename)
                    .link(UPLOAD_DIR + newFilename)
                    .orgName(fileName)
                    .pubId(userId)
                    .createdTimeAt(ZonedDateTime.now(ZoneId.of("Asia/Seoul")))
                    .fileSize(Math.round(file.getSize() * 0.0009765625))//KB
                    .level(1L)
                    .build();

            Share share = Share.builder()
                    .fileId(newFile.getId())
                    .userId(newFile.getPubId())
                    .level(0L)
                    .build();

            return fileInfoRepository.save(newFile);

        }
        else {
            Path path = Paths.get(UPLOAD_DIR);
            Files.copy(file.getInputStream(), path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

            Runtime rt = Runtime.getRuntime();
            Process p;
            String baseURL = System.getProperty("user.dir");
            String cmd = "python2 " + baseURL + "/unoconv/unoconv.py -i utf8 -f pdf --output=" + baseURL + "/converts/"
                    + newFilename + " " + baseURL + "/uploads/" + fileName;
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
                    .link(UPLOAD_DIR + newFilename)
                    .orgName(fileName)
                    .pubId(userId)
                    .createdTimeAt(ZonedDateTime.now(ZoneId.of("Asia/Seoul")))
                    .fileSize(Math.round(file.getSize() * 0.0009765625))//KB
                    .level(1L)
                    .build();

            Share share = Share.builder()
                    .fileId(newFile.getId())
                    .userId(newFile.getPubId())
                    .level(0L)
                    .build();

            return fileInfoRepository.save(newFile);
        }
    }

    public Resource loadAsResource(Long fileId) throws FileNotFoundException {
        try{
            FileInfo fileInfo = fileInfoRepository.findById(fileId).orElse(null);
            Path file = Paths.get(System.getProperty("user.dir")+"/uploads/"+fileInfo.getOrgName());
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            }
            else{
                throw new FileNotFoundException(
                        "Could not read file:" + fileInfo.getOrgName());
            }
        } catch (MalformedURLException | FileNotFoundException e) {
            throw new FileNotFoundException("Could not read file");
        }
    }

    public Resource loadAsPdfResource(Long fileId) throws FileNotFoundException {
        try{
            FileInfo fileInfo = fileInfoRepository.findById(fileId).orElse(null);
            Path file = Paths.get(System.getProperty("user.dir")+"/converts/"+fileInfo.getName());
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            }
            else{
                throw new FileNotFoundException(
                        "Could not read file:" + fileInfo.getOrgName());
            }
        } catch (MalformedURLException | FileNotFoundException e) {
            throw new FileNotFoundException("Could not read file");
        }
    }

    public String getFileName(Long fileId) {
        return fileInfoRepository.findById(fileId).orElse(null).getOrgName();
    }
    public String getPdfFileName(Long fileId) {
        return fileInfoRepository.findById(fileId).orElse(null).getName();
    }
}
