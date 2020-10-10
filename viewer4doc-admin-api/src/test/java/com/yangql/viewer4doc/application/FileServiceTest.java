package com.yangql.viewer4doc.application;

import com.yangql.viewer4doc.domain.AdminFile;
import com.yangql.viewer4doc.domain.FileInfo;
import com.yangql.viewer4doc.domain.FileInfoRepository;
import com.yangql.viewer4doc.domain.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
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

class FileServiceTest {
    private FileService fileService;

    @Mock
    private FileInfoRepository fileInfoRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockReturnRepository();
        fileService = new FileService(fileInfoRepository);
    }

    private void mockReturnRepository() {
        List<FileInfo> fileInfos = new ArrayList<>();
        FileInfo fileInfo = FileInfo.builder()
                .id(1L)
                .orgName("org.pdf")
                .name("new.pdf")
                .build();
        fileInfos.add(fileInfo);
        given(fileInfoRepository.findAll()).willReturn(fileInfos);
        given(fileInfoRepository.findById(1L)).willReturn(Optional.of(fileInfo));
    }
    @Test
    public void fileList(){
        List<FileInfo> fileInfos = fileService.getFiles();
        assertThat(fileInfos.get(0).getId(),is(1L));
        assertThat(fileInfos.get(0).getName(),is("new.pdf"));

    }
    @Test
    public void fileDetail(){
        FileInfo fileInfo = fileService.getFile(1L);
        assertThat(fileInfo.getName(),is("new.pdf"));
    }

    @Test
    public void createFile(){
        FileInfo fileInfo = FileInfo.builder()
                .id(1L)
                .name("name")
                .build();
        fileService.addFile(fileInfo);
        verify(fileInfoRepository).save(any());
    }
}