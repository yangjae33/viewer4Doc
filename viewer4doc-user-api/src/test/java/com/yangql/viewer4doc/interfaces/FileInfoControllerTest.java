package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.FileService;
import com.yangql.viewer4doc.domain.FileInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
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
}