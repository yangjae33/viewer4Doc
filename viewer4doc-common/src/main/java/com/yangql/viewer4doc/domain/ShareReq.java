package com.yangql.viewer4doc.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShareReq {
    private Long fileId;
    private Long level;
}
