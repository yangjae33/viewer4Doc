package com.yangql.viewer4doc.domain;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ShareRepository extends CrudRepository<Share,SharePK> {
    List<Share> findAllByUserId(Long userId);
    List<Share> findAllByFileId(Long fileId);
    Optional<Share> findByFileId(Long fileId);

    Share save(Share share);

    @Modifying
    @Query("UPDATE Share s SET s.level= :level where s.userId=:userId and s.fileId=:fileId")
    void updateShare(Long userId, Long fileId, Long level);
}
