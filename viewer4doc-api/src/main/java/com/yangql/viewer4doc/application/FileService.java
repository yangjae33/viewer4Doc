package com.yangql.viewer4doc.application;

import com.yangql.viewer4doc.domain.File;
import com.yangql.viewer4doc.domain.FileRepository;
import com.yangql.viewer4doc.domain.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    private static FileRepository fileRepository;
    public FileService(FileRepository userRepository){
        this.fileRepository = fileRepository;
    }
    public void addFile(File file){

    }

    public List<File> getFiles() {
        List<File> files = fileRepository.findAll();
        return files;
    }
}
