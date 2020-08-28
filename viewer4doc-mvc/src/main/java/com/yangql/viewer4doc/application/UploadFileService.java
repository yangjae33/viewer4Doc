package com.yangql.viewer4doc.application;

import com.yangql.viewer4doc.domain.FileInfo;
import com.yangql.viewer4doc.domain.FileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UploadFileService {
    private static FileRepository fileRepository;
    public UploadFileService(FileRepository fileRepository){
        this.fileRepository = fileRepository;
    }
    public void addFile(FileInfo fileInfo){
        fileRepository.save(fileInfo);
    }

    public List<FileInfo> getFiles() {
        List<FileInfo> fileInfos = fileRepository.findAll();
        return fileInfos;
    }


    public FileInfo getFile(Long id) {
        FileInfo fileInfo = fileRepository.findById(id).orElse(null);
        return fileInfo;
    }
}
