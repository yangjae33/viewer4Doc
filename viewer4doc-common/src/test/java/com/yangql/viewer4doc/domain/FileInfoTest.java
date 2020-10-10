package com.yangql.viewer4doc.domain;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class FileInfoTest {
    @Test
    public void creation(){
        FileInfo fileInfo = FileInfo.builder()
                .id(1L)
                .name("org.pdf")
                .orgName("org.pdf")
                .pubId(2L)
                .build();
        assertThat(fileInfo.getId(),is(1L));
    }
}