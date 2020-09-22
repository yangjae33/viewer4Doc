package com.yangql.viewer4doc.application;

import com.yangql.viewer4doc.domain.FileInfo;
import com.yangql.viewer4doc.domain.FileInfoRepository;
import com.yangql.viewer4doc.domain.Share;
import com.yangql.viewer4doc.domain.ShareRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    private static FileInfoRepository fileInfoRepository;
    private static ShareRepository shareRepository;
    public FileService(FileInfoRepository fileInfoRepository,ShareRepository shareRepository){
        this.shareRepository = shareRepository;
        this.fileInfoRepository = fileInfoRepository;
    }
    public FileInfo addFile(FileInfo fileInfo){
        return fileInfoRepository.save(fileInfo);
    }

    public List<FileInfo> getFiles() {
        List<FileInfo> fileInfos = fileInfoRepository.findAll();
        return fileInfos;
    }

    public FileInfo getFile(Long id) {
        FileInfo fileInfo = fileInfoRepository.findById(id).orElse(null);
        return fileInfo;
    }

    public List<FileInfo> getMyFiles(Long pub_id) {

        List<FileInfo> myfileInfos = fileInfoRepository.findAllByPubId(pub_id);

        return myfileInfos;
    }
    public Share addShare(Share share){
        return shareRepository.save(share);
    }
    public List<FileInfo> getSharedFiles(Long id){
        List<Share> shares = shareRepository.findAllByUserId(id);
        List<FileInfo> sharedfileInfos = new ArrayList<>();

        for(int i = 0; i<shares.size(); i++){
            FileInfo newFile = fileInfoRepository.findById(shares.get(i).getFileId()).orElse(null);
            if(newFile != null){
                sharedfileInfos.add(newFile);
            }
        }
        return sharedfileInfos;
    }

}
