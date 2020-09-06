package com.yangql.viewer4doc.interfaces;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ViewerController.class)
class ViewerControllerTest {
//    @Autowired
//    private MockMvc mvc;
//
//    @Test
//    public void show() throws Exception {
//        mvc.perform(get("/viewer4doc"))
//                .andExpect(status().isOk());
//    }
}