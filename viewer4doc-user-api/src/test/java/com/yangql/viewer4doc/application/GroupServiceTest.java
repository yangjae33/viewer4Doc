package com.yangql.viewer4doc.application;

import com.yangql.viewer4doc.domain.FileInfo;
import com.yangql.viewer4doc.domain.FileInfoRepository;
import com.yangql.viewer4doc.domain.GroupRepository;
import com.yangql.viewer4doc.domain.ShareRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

class GroupServiceTest {
    private GroupService groupService;
    private ShareService shareService;

    @Mock
    private FileInfoRepository fileInfoRepository;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private ShareRepository shareRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockReturnRepository();
        shareService = new ShareService(shareRepository);
        groupService = new GroupService(groupRepository);
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
}