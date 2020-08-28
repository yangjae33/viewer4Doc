package com.yangql.viewer4doc.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo {
    @Id
    @Setter
    @GeneratedValue
    private Long id;

    private String org_name;

    private String name;

    private String link;



}
