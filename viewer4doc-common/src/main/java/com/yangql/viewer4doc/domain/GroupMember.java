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
public class GroupMember {
    @Id
    @GeneratedValue
    private Long id;

    @Setter
    private Long groupId;

    @Setter
    private Long userId;

    @Setter
    private Long active;

    @Setter
    private Long level;
}
