package com.yangql.viewer4doc.application;

import com.yangql.viewer4doc.domain.GroupMember;
import com.yangql.viewer4doc.domain.GroupMemberRepository;
import com.yangql.viewer4doc.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class GroupMemberService {
    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Autowired
    private UserRepository userRepository;

    public void addGroupMembers(Long groupId, List<GroupMember> groupMember) {
        for(GroupMember u : groupMember){
            if(userRepository.findById(u.getUserId()).orElse(null) != null){
                update(groupId, u);
            }
        }
    }

    public void addGroupMember(Long groupId, GroupMember groupMember){
        update(groupId, groupMember);
    }
    private void update(Long groupId, GroupMember u) {
        u.setGroupId(groupId);
        groupMemberRepository.save(u);
    }

    public void deleteGroupMembers(Long groupId) {
        groupMemberRepository.deleteAllByGroupId(groupId);
    }
}
