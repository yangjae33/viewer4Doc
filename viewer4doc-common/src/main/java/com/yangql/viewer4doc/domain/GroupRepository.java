package com.yangql.viewer4doc.domain;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends CrudRepository<GroupInfo,Long> {
    List<GroupInfo> findAll();
    Optional<GroupInfo> findById(Long id);
    GroupInfo save(GroupInfo group);

    @Modifying
    @Query("DELETE FROM GroupInfo g where g.id = :id")
    void deleteAllById(@Param("id")Long id);

    List<GroupInfo> findAllBymId(Long mId);
}
