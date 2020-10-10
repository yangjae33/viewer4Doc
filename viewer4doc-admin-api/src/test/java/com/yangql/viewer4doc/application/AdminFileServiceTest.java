package com.yangql.viewer4doc.application;

import com.yangql.viewer4doc.domain.AdminFile;
import com.yangql.viewer4doc.domain.AdminFileRepository;
import com.yangql.viewer4doc.domain.FileInfo;
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


class AdminFileServiceTest {

    private AdminFileService adminFileService;

    @Mock
    private AdminFileRepository adminFileRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockReturnRepository();
        adminFileService = new AdminFileService(adminFileRepository);
    }

    private void mockReturnRepository() {
        List<AdminFile> adminFiles = new ArrayList<>();
        AdminFile adminFile = AdminFile.builder()
                .id(1L)
                .orgName("org.pdf")
                .name("new.pdf")
                .build();
        adminFiles.add(adminFile);
        given(adminFileRepository.findAll()).willReturn(adminFiles);
        given(adminFileRepository.findById(1L)).willReturn(Optional.of(adminFile));
    }
    @Test
    public void addFile(){
        FileInfo fileInfo = FileInfo.builder()
                .id(1L)
                .orgName("org.pdf")
                .name("new.pdf")
                .build();
    }
    @Test
    public void getFiles(){
        List<AdminFile> adminFiles = adminFileService.getFiles();
        AdminFile adminFile = adminFiles.get(0);
        assertThat(adminFile.getId(),is(1L));
    }
}