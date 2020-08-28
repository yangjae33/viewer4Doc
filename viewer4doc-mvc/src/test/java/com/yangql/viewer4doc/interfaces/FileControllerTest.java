package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.UploadFileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@WebAppConfiguration
@WebMvcTest(FileController.class)
class FileControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext wac;

    @MockBean
    private UploadFileService uploadFileService;

    @BeforeEach
    public void setUp(){
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mvc = builder.build();
    }
    @Test
    public void upload() throws Exception {
        String fileName = "test.txt";
        File file = new File(FileController.UPLOAD_DIR+fileName);
        file.delete();

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file",fileName,
                "text/plain", "test data".getBytes());
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/upload")
                .file(mockMultipartFile);

        mvc.perform(builder)
                .andExpect(redirectedUrl("/"))
                .andDo(MockMvcResultHandlers.print());

        Assertions.assertTrue(file.exists());
    }
}