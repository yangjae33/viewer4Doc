package com.yangql.viewer4doc.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupReq {
    private Long groupId;
    private Long managerId;
    private Long userId;
}