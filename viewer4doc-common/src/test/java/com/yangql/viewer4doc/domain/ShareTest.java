package com.yangql.viewer4doc.domain;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class ShareTest {
    @Test
    public void shareList(){
        Share share = new Share();
        share.setUserId(1L);
        share.setFileId(2L);
        share.setLevel(2L);

        assertThat(share.getFileId(),is(2L));
    }
}