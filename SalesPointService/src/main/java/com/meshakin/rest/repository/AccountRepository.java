package com.meshakin.rest.repository;

import com.meshakin.rest.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    @Modifying
    @Query(value = "DROP TABLE IF EXISTS account CASCADE", nativeQuery = true)
    void dropTable();

    @Modifying
    @Query(value = "TRUNCATE TABLE account CASCADE" , nativeQuery = true)
    void truncateTable();
}
