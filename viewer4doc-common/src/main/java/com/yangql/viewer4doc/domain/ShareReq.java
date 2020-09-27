package com.yangql.viewer4doc.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShareReq {
    private String email;
    private Long fileId;
    private Long level;
}
