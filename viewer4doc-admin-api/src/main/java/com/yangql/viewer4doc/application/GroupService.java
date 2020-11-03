package com.yangql.viewer4doc.application;

import com.yangql.viewer4doc.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private GroupMemberRepository groupMemberRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileService fileService;
    public GroupService(GroupRepository groupRepository){
        this.groupRepository = groupRepository;
    }

    public GroupInfo createGroup(Long userId,String name) {
        GroupInfo group = GroupInfo.builder()
                .mId(userId)
                .name(name)
                .build();
        List<GroupInfo> gis = groupRepository.findAllBymId(userId);
        //TODO : MAX GROUP CNT 상수로 선언할 것
        if(gis.size()>=5&&!(fileService.checkAdmin(userId))){
            throw new MaxGroupCountException();
        }
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
