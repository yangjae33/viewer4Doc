package com.yangql.viewer4doc.domain;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShareFileResponse {
    private FileInfo fileInfo;
    private Long level;
}
