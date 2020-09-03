package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.ConvertFileService;
import com.yangql.viewer4doc.application.UploadFileService;
import com.yangql.viewer4doc.application.UploadWithInvalidExtensionException;
import com.yangql.viewer4doc.domain.FileInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.*;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.longThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FileController.class)
class FileControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext wac;

    @MockBean
    private UploadFileService uploadFileService;

    @MockBean
    private ConvertFileService convertFileService;

    @BeforeEach
    public void setUp(){
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mvc = builder.build();
    }
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
        String fileName = "test.txt";
        File file = new File(FileController.UPLOAD_DIR+fileName);
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

        given(uploadFileService.uploadFile(mockMultipartFile)).willReturn(mockFile);

        mvc.perform(builder)
                .andExpect(status().isCreated());

        verify(uploadFileService).uploadFile(mockMultipartFile);
    }
    @Test
    public void uploadWithNotExistFileAPI() throws Exception {
        MockMultipartFile mockMultipartFile = null;

        given(uploadFileService.uploadFile(mockMultipartFile))
                .willThrow(UploadFileNotExistException.class);
    }

    @Test
    public void convert() throws IOException {
        Long fileId = 1L;
        convertFileService.convertFile(fileId);

        Runtime rt = Runtime.getRuntime();
        Process p;
        String baseURL = "/Users/mac/Desktop/";
        String TempFileName = "test";
        String fileName = "test.docx";
        String cmd = "python2 " + baseURL + "unoconv/unoconv.py -i utf8 -f pdf --output=" + baseURL + "converts/"
                + TempFileName + ".pdf " + baseURL + "uploads/" + fileName;
        p = rt.exec(cmd);

        InputStream in = p.getInputStream();
        InputStreamReader isr = new InputStreamReader(in);
        BufferedReader is = null;
        BufferedReader es = null;
        es = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        is = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = is.readLine()) != null) {
            System.out.println(line);
        }
        while ((line = es.readLine()) != null) {
            System.out.println(line);
        }
        if (is != null)
            is.close();
        if (es != null)
            es.close();


    }
}