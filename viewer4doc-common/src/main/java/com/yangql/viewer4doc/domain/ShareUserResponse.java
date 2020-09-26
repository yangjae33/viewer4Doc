package com.yangql.viewer4doc.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShareUserResponse {
    private UserInfo userInfo;
    private Long level;
}
