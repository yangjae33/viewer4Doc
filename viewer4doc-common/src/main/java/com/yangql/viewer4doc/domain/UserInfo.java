package com.yangql.viewer4doc.domain;

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
    private String name;

    @Setter
    private String email;

    private String password;

    @Setter
    @NotNull
    private Long level;

    private Long fileId;

    public String getPassword() {
        return password;
    }

    public boolean isPublisher() {
        return level==50L;
    }
    public boolean isActive(){
        return level>0L;
    }

    public void setFileId(Long fileId){
        this.level = 50L;
        this.fileId = fileId;
    }
}
