package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.UploadFileNotExistException;
import com.yangql.viewer4doc.application.UploadFileService;
import com.yangql.viewer4doc.domain.FileInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UploadFileController.class)
class UploadFileControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UploadFileService uploadFileService;

    @Test
    public void uploadFileAPI() throws Exception {
        String fileName = "test.txt";
        File file = new File(UploadFileController.UPLOAD_DIR+fileName);
        file.delete();

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file",fileName,
                "text/plain", "test data".getBytes());
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/api/upload")
                        .file(mockMultipartFile);

        FileInfo mockFile = FileInfo.builder()
                .name("test.txt")
                .link("*** AMAZON S3 Link ***")
                .org_name("test.txt")
                .build();

        given(uploadFileService.uploadFile(mockMultipartFile,1L)).willReturn(mockFile);

        mvc.perform(builder)
                .andExpect(status().isCreated());

        verify(uploadFileService).uploadFile(mockMultipartFile,1L);
    }
    @Test
    public void uploadWithNotExistFileAPI() throws Exception {
        MockMultipartFile mockMultipartFile = null;

        given(uploadFileService.uploadFile(mockMultipartFile,1L))
                .willThrow(UploadFileNotExistException.class);
    }
}