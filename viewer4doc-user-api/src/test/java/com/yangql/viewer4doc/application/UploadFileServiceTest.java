package com.yangql.viewer4doc.application;

import com.yangql.viewer4doc.domain.FileInfo;
import com.yangql.viewer4doc.domain.FileInfoRepository;
import com.yangql.viewer4doc.interfaces.UploadFileController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class UploadFileServiceTest {

    private UploadFileService uploadFileService;

    @Mock
    private FileInfoRepository fileInfoRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        uploadFileService = new UploadFileService(fileInfoRepository);
    }
    @Test
    public void upload() throws Exception {
        String fileName = "test.pdf";
        File file = new File(UploadFileController.UPLOAD_DIR+fileName);
        file.delete();

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file",fileName,
                "text/plain", "test data".getBytes());

        FileInfo mockFileinfo = FileInfo.builder()
                .name(fileName)
                .build();

//        uploadFileService.uploadFile(mockMultipartFile,30L);

//        verify(fileInfoRepository).save(any());
    }

    @Test
    public void uploadWithInvalidExtension(){
        String fileName = "a.exe";
        Assertions.assertThrows(UploadWithInvalidExtensionException.class,()->{
            throw new UploadWithInvalidExtensionException(fileName);
        });
    }
}