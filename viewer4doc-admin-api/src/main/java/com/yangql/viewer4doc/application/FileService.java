package com.yangql.viewer4doc.application;

import com.yangql.viewer4doc.domain.AdminFile;
import com.yangql.viewer4doc.domain.AdminFileRepository;
import com.yangql.viewer4doc.domain.FileInfo;
import com.yangql.viewer4doc.domain.FileInfoRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileService {
    private static AdminFileRepository adminFileRepository;
    public FileService(AdminFileRepository adminFileRepository){
        this.adminFileRepository = adminFileRepository;
    }
    public void addFile(AdminFile adminFile){
        adminFileRepository.save(adminFile);
    }

    public List<AdminFile> getFiles() {
        List<AdminFile> adminFiles = adminFileRepository.findAll();
        return adminFiles;
    }

    public AdminFile getFile(Long id) {
        AdminFile adminFile = adminFileRepository.findById(id).orElse(null);
        return adminFile;
    }

    public Resource loadAsResource(Long fileId) throws FileNotFoundException {
        try{
            AdminFile adminFile = adminFileRepository.findById(fileId).orElse(null);
            Path file = Paths.get(System.getProperty("user.dir")+"/adminfiles/"+adminFile.getOrgName());
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            }
            else{
                throw new FileNotFoundException(
                        "Could not read file:" + adminFile.getOrgName());
            }
        } catch (MalformedURLException | FileNotFoundException e) {
            throw new FileNotFoundException("Could not read file");
        }
    }

    public Resource loadAsPdfResource(Long fileId) throws FileNotFoundException {
        try{
            AdminFile adminFile = adminFileRepository.findById(fileId).orElse(null);
            Path file = Paths.get(System.getProperty("user.dir")+"/converts/"+adminFile.getName());
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            }
            else{
                throw new FileNotFoundException(
                        "Could not read file:" + adminFile.getOrgName());
            }
        } catch (MalformedURLException | FileNotFoundException e) {
            throw new FileNotFoundException("Could not read file");
        }
    }

    public String getFileName(Long fileId) {
        return adminFileRepository.findById(fileId).orElse(null).getOrgName();
    }
    public String getPdfFileName(Long fileId) {
        return adminFileRepository.findById(fileId).orElse(null).getName();
    }

    public List<AdminFile> getFileByPubId(Long id) {
        return adminFileRepository.findAllByPubId(id);
    }
}
