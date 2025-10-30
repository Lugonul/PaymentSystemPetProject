package com.meshakin.rest.repository;


import com.meshakin.rest.entity.AcquiringBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AcquiringBankRepository extends JpaRepository<AcquiringBank, Long> {
    @Modifying
    @Query(value = "DROP TABLE IF EXISTS acquiring_bank CASCADE", nativeQuery = true)
    void dropTable();
}