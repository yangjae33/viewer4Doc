package com.yangql.viewer4doc.application;

import com.yangql.viewer4doc.domain.FileInfo;
import com.yangql.viewer4doc.domain.FileInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    @Autowired
    private FileInfoRepository fileInfoRepository;
    public FileService(FileInfoRepository fileInfoRepository){
        this.fileInfoRepository = fileInfoRepository;
    }

    public List<FileInfo> getFiles() {
        return fileInfoRepository.findAll();
    }

    public FileInfo getFile(Long id) {
        return fileInfoRepository.findById(id).orElse(null);
    }
    public FileInfo addFile(FileInfo fileInfo){
        return fileInfoRepository.save(fileInfo);
    }
}
