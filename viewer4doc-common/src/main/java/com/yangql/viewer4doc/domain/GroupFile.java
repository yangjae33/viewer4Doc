package com.yangql.viewer4doc.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupFile {
    @Id
    @GeneratedValue
    private Long id;

    private Long groupId;

    private Long fileId;
}
