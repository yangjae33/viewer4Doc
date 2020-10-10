package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.AdminFileService;
import com.yangql.viewer4doc.application.FileService;
import com.yangql.viewer4doc.domain.FileInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
    public void list() throws Exception {
        List<FileInfo> fileInfos = new ArrayList<>();
        FileInfo fileInfo = FileInfo.builder()
                .id(1L)
                .build();
        fileInfos.add(fileInfo);

        given(fileService.getFiles()).willReturn(fileInfos);
        mvc.perform(get("/files"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1")
                ));
    }
    @Test
    public void create() throws Exception {
        FileInfo fileInfo = FileInfo.builder()
                .id(1L)
                .name("name")
                .build();
        given(fileService.addFile(fileInfo)).willReturn(fileInfo);

        mvc.perform(post("/files")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"name\":\"name\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Created"));
    }
    @Test
    public void delete(){
        //admin token
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjU0LCJlbWFpbCI6ImFkbWluIn0.FjZiIwpHdO27d2UduS6EQ3CssmEbNbSiCQ-EUNvPtKE";
    }
}