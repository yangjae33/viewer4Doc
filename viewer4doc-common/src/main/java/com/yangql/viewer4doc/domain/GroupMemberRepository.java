package com.yangql.viewer4doc.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMemberRepository extends CrudRepository<GroupMember,Long> {
    List<GroupMember> findAllByGroupId(Long groupId);

    void deleteById(Long id);
}
