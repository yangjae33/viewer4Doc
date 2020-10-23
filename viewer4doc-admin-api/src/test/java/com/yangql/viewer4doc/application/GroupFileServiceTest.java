package com.yangql.viewer4doc.application;

import com.yangql.viewer4doc.domain.FileInfo;
import com.yangql.viewer4doc.domain.FileInfoRepository;
import com.yangql.viewer4doc.domain.GroupFile;
import com.yangql.viewer4doc.domain.GroupFileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class GroupFileServiceTest {
    private GroupFileService groupFileService;

    @Mock
    private FileInfoRepository fileInfoRepository;
    @Mock
    private GroupFileRepository groupFileRepository;
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        //mockReturnRepository();
        groupFileService = new GroupFileService(groupFileRepository,fileInfoRepository);
    }
    @Test
    public void getFiles(){
        GroupFile groupFile = GroupFile.builder()
                .fileId(1L)
                .groupId(128L)
                .build();
        FileInfo fileInfo = FileInfo.builder()
                .id(1L)
                .name("name")
                .orgName("name")
                .build();
        List<FileInfo> fileInfos = new ArrayList<>();
        List<GroupFile> groupFiles = new ArrayList<>();
        groupFiles.add(groupFile);
        fileInfos.add(fileInfo);
        given(groupFileRepository.findAllByGroupId(128L)).willReturn(groupFiles);
        given(fileInfoRepository.findById(1L)).willReturn(Optional.of(fileInfo));
        List<FileInfo> fileInfoList= groupFileService.getGroupFiles(128L);
        assertThat(fileInfoList.get(0).getId(),is(1L));
    }
}