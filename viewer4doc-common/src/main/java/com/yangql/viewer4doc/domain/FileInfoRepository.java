package com.yangql.viewer4doc.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FileInfoRepository extends CrudRepository<FileInfo,Long> {
    List<FileInfo> findAll();
    Optional<FileInfo> findById(Long id);
    FileInfo save(FileInfo fileInfo);

    @Query("SELECT f FROM FileInfo f where f.pubId = :pubId")
    List<FileInfo> findAllByPubId(@Param("pubId") Long pubId);
}
