package com.yangql.viewer4doc.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class SharePK implements Serializable {
    private Long userId;
    private Long fileId;
}
