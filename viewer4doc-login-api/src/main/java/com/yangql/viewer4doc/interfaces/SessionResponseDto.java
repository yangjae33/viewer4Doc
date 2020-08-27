package com.yangql.viewer4doc.interfaces;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SessionResponseDto {
    private String accessToken;
}
