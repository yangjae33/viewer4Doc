package com.yangql.viewer4doc.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Setter
@Getter
@Builder
@IdClass(SharePK.class)
@NoArgsConstructor
@AllArgsConstructor
public class Share implements Serializable {

    @Id
    private Long userId;

    @Id
    private Long fileId;

    //0 : read + download + delete (uploader)
    //1 : read + download
    //2 : read
    private Long level;
}
