package com.yangql.viewer4doc.domain;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMemberRepository extends CrudRepository<GroupMember,Long> {
    List<GroupMember> findAllByGroupId(Long groupId);

    void deleteById(Long id);

    @Modifying
    @Query("DELETE FROM GroupMember gm WHERE gm.groupId=:groupId")
    void deleteAllByGroupId(@Param("groupId")Long groupId);
}
