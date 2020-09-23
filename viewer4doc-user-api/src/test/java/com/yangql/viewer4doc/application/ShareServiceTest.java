package com.yangql.viewer4doc.application;

import com.yangql.viewer4doc.domain.FileInfo;
import com.yangql.viewer4doc.domain.FileInfoRepository;
import com.yangql.viewer4doc.domain.Share;
import com.yangql.viewer4doc.domain.ShareRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class ShareServiceTest {

    private ShareService shareService;
    @Mock
    private FileInfoRepository fileInfoRepository;

    @Mock
    private ShareRepository shareRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockReturnRepository();
        shareService = new ShareService(shareRepository);
    }

    private void mockReturnRepository() {
        List<FileInfo> fileInfos = new ArrayList<>();
        FileInfo fileInfo = FileInfo.builder()
                .id(1L)
                .org_name("org.pdf")
                .name("new.pdf")
                .build();
        fileInfos.add(fileInfo);
        given(fileInfoRepository.findAll()).willReturn(fileInfos);
        given(fileInfoRepository.findById(1L)).willReturn(Optional.of(fileInfo));
    }
    @Test
    public void addShare(){
        Share share = Share.builder()
                .userId(1L)
                .fileId(2L)
                .level(1L)
                .build();
        shareService.addShare(share);
        verify(shareRepository).save(any());
    }
}