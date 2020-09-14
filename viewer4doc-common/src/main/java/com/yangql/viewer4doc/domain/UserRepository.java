package com.yangql.viewer4doc.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<UserInfo,Long> {
    List<UserInfo> findAll();
    Optional<UserInfo> findById(Long id);
    UserInfo save(UserInfo userInfo);
    Optional<UserInfo> findByEmail(String email);
}
