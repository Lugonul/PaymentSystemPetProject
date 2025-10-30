package com.meshakin.rest.repository;

import com.meshakin.rest.entity.UserAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccessRepository extends JpaRepository<UserAccess,Long> {
    @Modifying
    @Query(value = "DROP TABLE IF EXISTS user_access CASCADE", nativeQuery = true)
    void dropTable();

    Optional<UserAccess> findByUserLogin(String userLogin);
}