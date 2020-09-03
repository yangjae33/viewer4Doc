package com.yangql.viewer4doc.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FileInfoRepository extends CrudRepository<FileInfo,Long> {
    List<FileInfo> findAll();
    Optional<FileInfo> findById(Long id);
    FileInfo save(FileInfo fileInfo);
}
