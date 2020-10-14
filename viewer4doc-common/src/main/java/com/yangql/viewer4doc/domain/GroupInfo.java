package com.yangql.viewer4doc.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupInfo{

    @Id
    @GeneratedValue
    private Long id;

    private Long mId;

    private String name;

    //TODO : thumbnail img

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<GroupMember> userList;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<FileInfo> fileList;

    public void addMember(GroupMember u){
        userList.add(u);
    }

    public void setMember(List<GroupMember> userList){
        this.userList = new ArrayList<>();
        for( GroupMember u : userList){
            addMember(u);
        }
    }
}
