package com.yangql.viewer4doc.application;

import com.yangql.viewer4doc.domain.FileInfo;
import com.yangql.viewer4doc.domain.FileInfoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    private static FileInfoRepository fileInfoRepository;
    public FileService(FileInfoRepository fileInfoRepository){
        this.fileInfoRepository = fileInfoRepository;
    }
    public void addFile(FileInfo fileInfo){
        fileInfoRepository.save(fileInfo);
    }

    public List<FileInfo> getFiles() {
        List<FileInfo> fileInfos = fileInfoRepository.findAll();
        return fileInfos;
    }


    public FileInfo getFile(Long id) {
        FileInfo fileInfo = fileInfoRepository.findById(id).orElse(null);
        return fileInfo;
    }
}
