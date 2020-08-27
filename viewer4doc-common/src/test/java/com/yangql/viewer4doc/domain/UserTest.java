package com.yangql.viewer4doc.domain;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    public void creation(){
        User user = User.builder()
                .name("jaehyuk")
                .email("a@gmail.com")
                .build();
        assertThat(user.getName(),is("jaehyuk"));
    }

}