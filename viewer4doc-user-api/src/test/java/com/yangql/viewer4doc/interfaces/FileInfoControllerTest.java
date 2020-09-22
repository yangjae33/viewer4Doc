package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.FileService;
import com.yangql.viewer4doc.domain.FileInfo;
import com.yangql.viewer4doc.domain.Share;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FileController.class)
class FileInfoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FileService fileService;

    @Test
    public void list() throws Exception {
        List<FileInfo> fileInfos = new ArrayList<>();
        fileInfos.add(FileInfo.builder()
                .id(1L)
                .org_name("org.pdf")
                .name("new.pdf")
                .link("a/b/c/d")
                .build()
        );
        given(fileService.getFiles()).willReturn(fileInfos);

        mvc.perform(get("/api/files"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1")
                ));
    }
    @Test
    public void detail() throws Exception {
        FileInfo fileInfo = FileInfo.builder()
                .id(1L)
                .org_name("org.pdf")
                .link("new.pdf")
                .name("new.pdf")
                .build();
        given(fileService.getFile(1L)).willReturn(fileInfo);

        mvc.perform(get("/api/file/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1")
                ));
    }

    @Test
    public void myfiles() throws Exception {
        //userId : 30L, email : test1
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjMwLCJlbWFpbCI6InRlc3QxIn0.uVHzuqkAwnxdOcH9TMju1RcbbfeqVaVJ_y5fwVoCfeY";

        FileInfo fileInfo = FileInfo.builder()
                .id(1L)
                .org_name("org.pdf")
                .link("new.pdf")
                .name("new.pdf")
                .pub_id(30L)
                .build();

        List<FileInfo> fileInfos = new ArrayList<>();
        fileInfos.add(fileInfo);

        given(fileService.getMyFiles(30L)).willReturn(fileInfos);

        mvc.perform(get("/api/myfiles")
                .header("Authorization", ":Bearer "+token)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1")
                ));
    }
//    @Test
//    public void sharedfiles() throws Exception {
//        List<FileInfo> myFileInfo = new ArrayList<>();
//        FileInfo fileInfo = FileInfo.builder()
//                .id(1L)
//                .org_name("org.pdf")
//                .link("new.pdf")
//                .name("new.pdf")
//                .pub_id(1L)
//                .build();
//        myFileInfo.add(fileInfo);
//        given(fileService.getMyFiles(1L)).willReturn(myFileInfo);
//        mvc.perform(get("/api/myfiles"))
//                .andExpect(status().isOk())
//                .andExpect(content().string(
//                        containsString("\"id\":1")
//                ));
//    }
}