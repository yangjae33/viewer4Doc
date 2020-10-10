package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.AdminFileService;
import com.yangql.viewer4doc.domain.FileInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminFileController.class)
class AdminFileControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AdminFileService adminFileService;

//    @Test
//    public void list() throws Exception {
//        List<FileInfo> fileInfos = new ArrayList<>();
//        fileInfos.add(FileInfo.builder()
//                .id(1L)
//                .orgName("org.pdf")
//                .name("new.pdf")
//                .link("a/b/c/d")
//                .build()
//        );
//
//        mvc.perform(get("/admin/files"))
//                .andExpect(status().isOk())
//                .andExpect(content().string(
//                        containsString("\"id\":1")
//                ));
//    }
//    @Test
//    public void detail() throws Exception {
//        FileInfo fileInfo = FileInfo.builder()
//                .id(1L)
//                .level(1L)
//                .orgName("org.pdf")
//                .link("new.pdf")
//                .name("new.pdf")
//                .build();
//
//        mvc.perform(get("/admin/file/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().string(
//                        containsString("\"id\":1")
//                ));
//    }
}