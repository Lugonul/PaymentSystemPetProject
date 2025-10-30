package com.meshakin.rest.repository;

import com.meshakin.rest.entity.IssuingBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IssuingBankRepository extends JpaRepository<IssuingBank,Long> {
    @Modifying
    @Query(value = "DROP TABLE IF EXISTS issuing_bank CASCADE", nativeQuery = true)
    void dropTable();

    @Modifying
    @Query(value = "TRUNCATE TABLE issuing_bank CASCADE" , nativeQuery = true)
    void truncateTable();
}
