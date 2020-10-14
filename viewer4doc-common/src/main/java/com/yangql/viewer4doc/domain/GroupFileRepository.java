package com.yangql.viewer4doc.domain;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupFileRepository extends CrudRepository<GroupFile,Long> {
    List<GroupFile> findAll();
    Optional<GroupFile> findById(Long id);
    GroupFile save(GroupFile group);
    List<GroupFile> findAllByGroupId(Long groupId);

    @Modifying
    @Query("DELETE FROM GroupFile g where g.id = :id")
    void deleteAllById(@Param("id")Long id);

}
