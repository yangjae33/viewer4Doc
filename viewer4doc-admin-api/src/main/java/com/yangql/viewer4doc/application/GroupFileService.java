package com.yangql.viewer4doc.application;

import com.yangql.viewer4doc.domain.FileInfo;
import com.yangql.viewer4doc.domain.FileInfoRepository;
import com.yangql.viewer4doc.domain.GroupFile;
import com.yangql.viewer4doc.domain.GroupFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupFileService {
    GroupFileRepository groupFileRepository;
    FileInfoRepository fileInfoRepository;

    GroupFileService(GroupFileRepository groupFileRepository,FileInfoRepository fileInfoRepository){
        this.groupFileRepository = groupFileRepository;
        this.fileInfoRepository = fileInfoRepository;
    }
    public List<FileInfo> getGroupFiles(Long groupId) {
        List<GroupFile> groupFiles = groupFileRepository.findAllByGroupId(groupId);
        List<FileInfo> resFiles = new ArrayList<>();
        for (GroupFile gf : groupFiles){
            FileInfo fileInfo = fileInfoRepository.findById(gf.getFileId()).orElse(null);
            if(fileInfo!=null){
                resFiles.add(fileInfo);
            }
        }
        return resFiles;
    }
}
