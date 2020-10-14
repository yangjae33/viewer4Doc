package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.AdminFileService;
import com.yangql.viewer4doc.application.FileService;
import com.yangql.viewer4doc.domain.FileInfo;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FileController.class)
class FileControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private FileService fileService;

//    @MockBean
//    private ShareService shareService;
    @BeforeEach
    public void setUp() throws Exception{
        initMocks(this);
    }

    @Test
    public void list() throws Exception {
        List<FileInfo> fileInfos = new ArrayList<>();
        FileInfo fileInfo = FileInfo.builder()
                .id(1L)
                .build();
        fileInfos.add(fileInfo);

        given(fileService.getFiles()).willReturn(fileInfos);
        mvc.perform(get("/admin/files"))
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

        mvc.perform(post("/admin/files")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"name\":\"name\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Created"));
    }
    @Test
    public void delete() throws Exception {
        //admin token
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjU0LCJlbWFpbCI6ImFkbWluIn0.FjZiIwpHdO27d2UduS6EQ3CssmEbNbSiCQ-EUNvPtKE";
        given(fileService.deleteAllFiles(1L,100L)).willReturn("Deleted");
        mvc.perform(post("/admin/files/1")
        .header("Authorization",":Bearer"+token))
                .andExpect(status().isOk());
    }
    @Test
    public void deleteWithNotAdmin() throws Exception {
        //admin token
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjU0LCJlbWFpbCI6ImFkbWluIn0.FjZiIwpHdO27d2UduS6EQ3CssmEbNbSiCQ-EUNvPtKE";
        given(fileService.deleteAllFiles(1L,99L)).willReturn("Deleted");
        mvc.perform(post("/admin/files/1")
                .header("Authorization",":Bearer"+token))
                .andExpect(status().isOk());
    }
}