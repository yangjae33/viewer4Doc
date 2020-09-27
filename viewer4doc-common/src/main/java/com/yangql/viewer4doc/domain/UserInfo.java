package com.yangql.viewer4doc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    @Id
    @GeneratedValue
    private Long id;

    @Setter
    private String email;

    @Setter
    private String name;

    private String password;

    @Setter
    @NotNull
    @JsonIgnore
    private Long level;

    @JsonIgnore
    public boolean isPublisher() {
        return level==50L;
    }
    @JsonIgnore
    public boolean isActive(){
        return level>0L;
    }
    @JsonIgnore
    public boolean isCorp(){
        return level>=100L;
    }
}
