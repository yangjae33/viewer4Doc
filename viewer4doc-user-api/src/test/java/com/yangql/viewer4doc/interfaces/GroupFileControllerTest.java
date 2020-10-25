package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.GroupFileService;
import com.yangql.viewer4doc.application.GroupService;
import com.yangql.viewer4doc.domain.FileInfo;
import com.yangql.viewer4doc.domain.GroupFile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GroupFileController.class)
class GroupFileControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private GroupService groupService;
    @MockBean
    private GroupFileService groupFileService;
    @Test
    public void getFiles() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjU0LCJlbWFpbCI6ImFkbWluIn0.FjZiIwpHdO27d2UduS6EQ3CssmEbNbSiCQ-EUNvPtKE";

        List<GroupFile> groupFileList = new ArrayList<>();
        List<FileInfo> fileInfoList = new ArrayList<>();
        FileInfo fileInfo = FileInfo.builder()
                .name("file.pdf")
                .orgName("file.docx")
                .fileSize(1L)
                .build();
        GroupFile groupFile = GroupFile.builder()
                .fileId(1L)
                .groupId(1L)
                .build();
        groupFileList.add(groupFile);
        fileInfoList.add(fileInfo);
        given(groupFileService.getGroupFiles(1L)).willReturn(fileInfoList);
        mvc.perform(get("/api/group/1/files").header("Authorization",":Bearer"+token)).andExpect(status().isOk());
    }
}