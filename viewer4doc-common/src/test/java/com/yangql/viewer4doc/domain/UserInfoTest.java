package com.yangql.viewer4doc.domain;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class UserInfoTest {

    @Test
    public void creation(){
        UserInfo userInfo = UserInfo.builder()
                .name("jaehyuk")
                .email("a@gmail.com")
                .build();
        assertThat(userInfo.getName(),is("jaehyuk"));
    }

}