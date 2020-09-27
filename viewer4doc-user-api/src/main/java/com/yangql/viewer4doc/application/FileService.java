package com.yangql.viewer4doc.application;

import com.yangql.viewer4doc.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class FileService {
    @Autowired
    private UserService userService;

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
    public List<ShareFileResponse> getSharedFiles(Long id){
        List<Share> shares = shareRepository.findAllByUserId(id);
        List<ShareFileResponse> sharedfileInfos = new ArrayList<>();

        for(int i = 0; i<shares.size(); i++){
            FileInfo newFile = fileInfoRepository.findById(shares.get(i).getFileId()).orElse(null);
            Long scope = shares.get(i).getLevel();
            UserInfo userInfo = userService.getUserById(newFile.getPubId());
            if(userInfo == null)
                continue;

            ShareFileResponse fileAddedScope = ShareFileResponse.builder()
                    .fileInfo(newFile)
                    .level(scope)
                    .userInfo(userInfo)
                    .build();

            if(newFile != null){
                sharedfileInfos.add(fileAddedScope);
            }
        }
        return sharedfileInfos;
    }

    public void deleteAllFiles(Long fileId) {
        fileInfoRepository.deleteAllById(fileId);
    }

    public boolean checkUserFile(Long userId, Long fileId) {
        FileInfo fileInfo = fileInfoRepository.findById(fileId).orElse(null);
        if(fileInfo == null){
            return false;
        }
        if(fileInfo.getPubId() != userId){
            return false;
        }
        return true;
    }
}
