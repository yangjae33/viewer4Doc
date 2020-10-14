package com.yangql.viewer4doc.application;

import com.yangql.viewer4doc.domain.GroupMember;
import com.yangql.viewer4doc.domain.GroupMemberRepository;
import com.yangql.viewer4doc.domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupMemberService {
    @Autowired
    private GroupMemberRepository groupMemberRepository;

    public void addGroupMember(Long groupId, List<GroupMember> groupMember) {
        for(GroupMember u : groupMember){
            update(groupId, u);
        }
    }

    private void update(Long groupId, GroupMember u) {
        u.setGroupId(groupId);
        u.setActive(0L);
        groupMemberRepository.save(u);
    }
}
