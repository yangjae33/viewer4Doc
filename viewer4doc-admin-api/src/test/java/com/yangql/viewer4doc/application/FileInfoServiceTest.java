package com.yangql.viewer4doc.application;

import com.yangql.viewer4doc.domain.FileInfo;
import com.yangql.viewer4doc.domain.FileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import static org.mockito.BDDMockito.given;


class FileInfoServiceTest {

    private FileService fileService;

    @Mock
    private FileRepository fileRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockReturnRepository();
        fileService = new FileService(fileRepository);
    }

    private void mockReturnRepository() {
        List<FileInfo> fileInfos = new ArrayList<>();
        FileInfo fileInfo = FileInfo.builder()
                .id(1L)
                .org_name("org.pdf")
                .name("new.pdf")
                .build();
        fileInfos.add(fileInfo);
        given(fileRepository.findAll()).willReturn(fileInfos);
        given(fileRepository.findById(1L)).willReturn(Optional.of(fileInfo));
    }
    @Test
    public void addFile(){
        FileInfo fileInfo = FileInfo.builder()
                .id(1L)
                .org_name("org.pdf")
                .name("new.pdf")
                .build();
    }
    @Test
    public void getFiles(){
        List<FileInfo> fileInfos = fileService.getFiles();
        FileInfo fileInfo = fileInfos.get(0);
        assertThat(fileInfo.getId(),is(1L));
    }
}