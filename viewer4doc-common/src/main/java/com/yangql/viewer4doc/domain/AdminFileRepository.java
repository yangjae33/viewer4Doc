package com.yangql.viewer4doc.domain;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminFileRepository extends CrudRepository<AdminFile,Long> {
    List<AdminFile> findAll();
    Optional<AdminFile> findById(Long id);
    AdminFile save(AdminFile adminFile);

    @Query("SELECT f FROM AdminFile f where f.pubId = :pubId")
    List<AdminFile> findAllByPubId(@Param("pubId") Long pubId);

    @Modifying
    @Query("DELETE FROM AdminFile f where f.id = :id")
    void deleteAllById(@Param("id")Long id);
}
