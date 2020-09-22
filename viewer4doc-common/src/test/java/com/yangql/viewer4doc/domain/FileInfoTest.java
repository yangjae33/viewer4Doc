package com.yangql.viewer4doc.domain;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class FileInfoTest {
    @Test
    public void creation(){
        FileInfo fileInfo = new FileInfo(1L,"org.pdf","new.pdf","a/b/c/d",1L);
        assertThat(fileInfo.getId(),is(1L));
    }
}