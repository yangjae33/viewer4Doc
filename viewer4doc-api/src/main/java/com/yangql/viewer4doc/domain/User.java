package com.yangql.viewer4doc.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @Setter
    @GeneratedValue
    private long id;
    private String name;
    private String email;
}
