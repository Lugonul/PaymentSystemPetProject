package com.meshakin.rest.repository;

import com.meshakin.rest.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionTypeRepository extends JpaRepository<TransactionType, Long> {
    @Modifying
    @Query(value = "DROP TABLE IF EXISTS transaction_type CASCADE", nativeQuery = true)
    void dropTable();
}