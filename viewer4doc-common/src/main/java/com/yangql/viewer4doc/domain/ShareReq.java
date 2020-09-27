package com.yangql.viewer4doc.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShareReq {
    private String email;
    private Long fileId;
    private Long level;
}
