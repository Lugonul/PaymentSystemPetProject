package com.meshakin.rest.repository;

import com.meshakin.rest.entity.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType,Long> {
    @Modifying
    @Query(value = "DROP TABLE IF EXISTS account_type CASCADE", nativeQuery = true)
    void dropTable();
}