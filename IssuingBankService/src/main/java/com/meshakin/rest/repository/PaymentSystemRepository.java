package com.meshakin.rest.repository;

import com.meshakin.rest.entity.PaymentSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentSystemRepository extends JpaRepository<PaymentSystem,Long> {
    @Modifying
    @Query(value = "DROP TABLE IF EXISTS payment_system CASCADE", nativeQuery = true)
    void dropTable();
}