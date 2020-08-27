package com.yangql.viewer4doc.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends CrudRepository<File,Long> {
    List<File> findAll();
    Optional<File> findById(Long id);
    File save(File file);
}
