package com.yangql.viewer4doc.application;

import com.yangql.viewer4doc.domain.File;
import com.yangql.viewer4doc.domain.FileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;


class FileServiceTest {

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
        List<File> files = new ArrayList<>();
        File file = File.builder()
                .id(1L)
                .org_name("org.pdf")
                .name("new.pdf")
                .build();
        files.add(file);
        given(fileRepository.findAll()).willReturn(files);
        given(fileRepository.findById(1L)).willReturn(Optional.of(file));
    }
    @Test
    public void addFile(){
        File file = File.builder()
                .id(1L)
                .org_name("org.pdf")
                .name("new.pdf")
                .build();
    }
    @Test
    public void getFiles(){
        List<File> files = fileService.getFiles();
        File file = files.get(0);
        assertThat(file.getId(),is(1L));
    }
}