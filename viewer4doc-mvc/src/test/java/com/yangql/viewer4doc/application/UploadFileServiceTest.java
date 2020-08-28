package com.yangql.viewer4doc.application;

import com.yangql.viewer4doc.domain.FileInfo;
import com.yangql.viewer4doc.domain.FileRepository;
import com.yangql.viewer4doc.interfaces.FileController;
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
    private FileRepository fileRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        uploadFileService = new UploadFileService(fileRepository);
    }
    @Test
    public void upload() throws Exception {
        String fileName = "test.txt";
        File file = new File(FileController.UPLOAD_DIR+fileName);
        file.delete();

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file",fileName,
                "text/plain", "test data".getBytes());

        FileInfo newFile = uploadFileService.uploadFile(mockMultipartFile);

        given(fileRepository.save(newFile)).willReturn(newFile);

        verify(fileRepository).save(any());

        Assertions.assertTrue(file.exists());
    }
}