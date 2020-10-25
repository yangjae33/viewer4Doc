package com.yangql.viewer4doc.application;

import com.yangql.viewer4doc.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private GroupMemberRepository groupMemberRepository;
    @Autowired
    private UserRepository userRepository;

    public GroupService(GroupRepository groupRepository){
        this.groupRepository = groupRepository;
    }

    public GroupInfo createGroup(Long userId,String name) {
        GroupInfo group = GroupInfo.builder()
                .mId(userId)
                .name(name)
                .build();
        return groupRepository.save(group);
    }

    public GroupInfo getGroup(Long groupId) {
        GroupInfo group =  groupRepository.findById(groupId).orElse(null);

        List<GroupMember> groupMembers = groupMemberRepository.findAllByGroupId(groupId);

        group.setMember(groupMembers);

        return group;
    }
    public List<GroupInfo> getGroups(){
        List<GroupInfo> groups = groupRepository.findAll();
        return groups;
    }

    public void deleteGroup(Long groupId) {
        groupRepository.deleteAllById(groupId);
    }
}
