package com.yangql.viewer4doc.domain;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

class FileTest {
    @Test
    public void creation(){
        File file = new File(1L,"org.pdf","new.pdf","a/b/c/d");
        assertThat(file.getId(),is(1L));
    }
}