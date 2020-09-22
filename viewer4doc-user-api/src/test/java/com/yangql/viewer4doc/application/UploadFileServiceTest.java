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

        String fileName = "test.docx";
        File file = new File(UploadFileController.UPLOAD_DIR+fileName);
        file.delete();

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file",fileName,
                "text/plain", "test data".getBytes());

        FileInfo mockFileinfo = FileInfo.builder()
                .name(fileName)
                .build();

        given(fileInfoRepository.save(mockFileinfo)).willReturn(mockFileinfo);

        FileInfo newFile = uploadFileService.uploadFile(mockMultipartFile,1L);

        verify(fileInfoRepository).save(any());

        Assertions.assertTrue(file.exists());
    }

    @Test
    public void uploadWithInvalidExtension(){
        String fileName = "a.exe";
        Assertions.assertThrows(UploadWithInvalidExtensionException.class,()->{
            throw new UploadWithInvalidExtensionException(fileName);
        });
    }
}