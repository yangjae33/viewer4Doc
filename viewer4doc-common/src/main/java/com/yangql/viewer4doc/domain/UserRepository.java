package com.yangql.viewer4doc.domain;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserInfo,Long> {
    List<UserInfo> findAll();
    Optional<UserInfo> findById(Long id);
    UserInfo save(UserInfo userInfo);

    @Query("SELECT u FROM UserInfo u where u.email=:email")
    Optional<UserInfo> findByEmail(String email);

    @Modifying
    @Query("UPDATE UserInfo u SET u.level=-1L where u.id=:id")
    void deactivateUser(Long id);
}
