package com.yangql.viewer4doc.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ShareRepository extends CrudRepository<Share,SharePK> {
    List<Share> findAllByUserId(Long userId);
}
