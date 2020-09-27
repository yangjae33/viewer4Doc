package com.yangql.viewer4doc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileInfo {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String orgName;

    private Long fileSize;

    private ZonedDateTime createdTimeAt;

    private String link;

    private Long pubId;

    private Long level;

    @JsonIgnore
    public boolean isActive(){
        return level>0L;
    }
}
