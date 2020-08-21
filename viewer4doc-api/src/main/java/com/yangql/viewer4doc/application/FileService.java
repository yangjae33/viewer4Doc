package com.yangql.viewer4doc.application;

import com.yangql.viewer4doc.domain.File;
import com.yangql.viewer4doc.domain.FileRepository;
import com.yangql.viewer4doc.domain.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FileService {
    private static FileRepository fileRepository;
    public FileService(FileRepository fileRepository){
        this.fileRepository = fileRepository;
    }
    public void addFile(File file){
        fileRepository.save(file);
    }

    public List<File> getFiles() {
        List<File> files = fileRepository.findAll();
        return files;
    }


    public File getFile(Long id) {
        File file = fileRepository.findById(id).orElse(null);
        return file;
    }
}
