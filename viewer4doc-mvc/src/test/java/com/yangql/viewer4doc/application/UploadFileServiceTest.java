package com.yangql.viewer4doc.application;

import com.yangql.viewer4doc.domain.FileInfo;
import com.yangql.viewer4doc.domain.FileRepository;
import com.yangql.viewer4doc.domain.User;
import com.yangql.viewer4doc.interfaces.FileController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

        String fileName = "test.docx";
        File file = new File(FileController.UPLOAD_DIR+fileName);
        file.delete();

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file",fileName,
                "text/plain", "test data".getBytes());

        FileInfo mockFileinfo = FileInfo.builder()
                .name(fileName)
                .build();

        given(fileRepository.save(mockFileinfo)).willReturn(mockFileinfo);

        FileInfo newFile = uploadFileService.uploadFile(mockMultipartFile);

        verify(fileRepository).save(any());

        Assertions.assertTrue(file.exists());
    }
    @Test
    public void uploadWithNoFile() throws IOException {
        String fileName = null;
        Assertions.assertThrows(UploadFileNotExistException.class,()->{
            throw new UploadFileNotExistException();
        });

    }
    @Test
    public void uploadWithInvalidExtension(){
        String fileName = "a.exe";
        Assertions.assertThrows(UploadWithInvalidExtensionException.class,()->{
            throw new UploadWithInvalidExtensionException(fileName);
        });
    }
}