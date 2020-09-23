package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.UploadFileNotExistException;
import com.yangql.viewer4doc.application.FileService;
import com.yangql.viewer4doc.domain.FileInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.io.*;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FileController.class)
class FileControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FileService fileService;

    @Test
    public void uploadFileOnWebPage() throws Exception {
        String fileName = "test.txt";
        File file = new File(FileController.UPLOAD_DIR+fileName);
        file.delete();

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file",fileName,
                "text/plain", "test data".getBytes());
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/web/upload")
                .file(mockMultipartFile);

        mvc.perform(builder)
                .andExpect(redirectedUrl("/"))
                .andDo(MockMvcResultHandlers.print());

        Assertions.assertTrue(file.exists());
    }
    @Test
    public void uploadFileAPI() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjMwLCJlbWFpbCI6InRlc3QxIn0.uVHzuqkAwnxdOcH9TMju1RcbbfeqVaVJ_y5fwVoCfeY";

        String fileName = "test.docx";
        File file = new File(FileController.UPLOAD_DIR+fileName);
        file.delete();

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file",fileName,
                "text/plain", "test data".getBytes());
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders
                        .multipart("/api/upload")
                        .file(mockMultipartFile)
                        .header("Authorization", ":Bearer "+token)
                        .contentType(MediaType.APPLICATION_JSON);

        FileInfo mockFile = FileInfo.builder()
                .name("test.txt")
                .link("*** AMAZON S3 Link ***")
                .org_name("test.txt")
                .build();

        given(fileService.uploadFile(mockMultipartFile,30L)).willReturn(mockFile);

        mvc.perform(builder)
                .andExpect(status().isCreated());

        verify(fileService).uploadFile(mockMultipartFile,30L);

    }
    @Test
    public void uploadWithNotExistFileAPI() throws Exception {
        MockMultipartFile mockMultipartFile = null;

        given(fileService.uploadFile(mockMultipartFile,30L))
                .willThrow(UploadFileNotExistException.class);
    }
//    @Test
//    public void downloadFile() throws Exception {
//        Long fileId = 1L;
//        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjMwLCJlbWFpbCI6InRlc3QxIn0.uVHzuqkAwnxdOcH9TMju1RcbbfeqVaVJ_y5fwVoCfeY";
//
//        mvc.perform(get("/api/download/60")
//                .header("Authorization", ":Bearer "+token)
//                .contentType(MediaType.APPLICATION_JSON)
//        )
//                .andExpect(status().isOk())
//                .andExpect(content().string(
//                        containsString("\"id\":1")
//                ));
//    }
}