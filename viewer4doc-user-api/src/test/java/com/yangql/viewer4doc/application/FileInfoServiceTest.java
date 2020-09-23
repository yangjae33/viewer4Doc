package com.yangql.viewer4doc.application;

import com.yangql.viewer4doc.domain.FileInfo;
import com.yangql.viewer4doc.domain.FileInfoRepository;
import com.yangql.viewer4doc.domain.Share;
import com.yangql.viewer4doc.domain.ShareRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


class FileInfoServiceTest {

    private FileService fileService;

    @Mock
    private FileInfoRepository fileInfoRepository;

    @Mock
    private ShareRepository shareRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockReturnRepository();
        fileService = new FileService(fileInfoRepository,shareRepository);
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
    public void addFile(){
        FileInfo fileInfo = FileInfo.builder()
                .id(1L)
                .org_name("org.pdf")
                .name("new.pdf")
                .build();
        fileService.addFile(fileInfo);
        verify(fileInfoRepository).save(any());
    }

    @Test
    public void getFiles(){
        List<FileInfo> fileInfos = fileService.getFiles();
        FileInfo fileInfo = fileInfos.get(0);
        assertThat(fileInfo.getId(),is(1L));
    }
    @Test
    public void getMyFiles(){
        FileInfo fileInfo = FileInfo.builder()
                .id(2L)
                .link(" ")
                .name(" ")
                .pub_id(1L)
                .build();
        List<FileInfo> fileInfos = new ArrayList<>();
        fileInfos.add(fileInfo);
        given(fileInfoRepository.findAllByPubId(1L)).willReturn(fileInfos);

        assertThat(fileInfos.get(0).getId(),is(2L));
    }
    @Test
    public void getSharedFiles(){
        Long id = 1L;
        FileInfo fileInfo = FileInfo.builder()
                .id(2L)
                .link(" ")
                .name(" ")
                .pub_id(1L)
                .build();

        Share share = Share.builder()
                .fileId(2L)
                .userId(1L)
                .level(2L)
                .build();
        List<Share> shares = new ArrayList<>();
        shares.add(share);
        given(fileInfoRepository.findById(2L)).willReturn(Optional.of(fileInfo));
        given(shareRepository.findAllByUserId(1L)).willReturn(shares);

        List<FileInfo> sharedfileInfos = new ArrayList<>();

        for(int i = 0; i<shares.size(); i++){
            FileInfo newFile = fileInfoRepository.findById(shares.get(i).getFileId()).orElse(null);
            if(newFile != null){
                sharedfileInfos.add(newFile);
            }
        }
        assertThat(sharedfileInfos.get(0).getId(),is(2L));
    }
}