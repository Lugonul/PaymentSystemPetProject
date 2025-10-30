package com.meshakin.rest.repository;

import com.meshakin.rest.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency,Long> {
    @Modifying
    @Query(value = "DROP TABLE IF EXISTS currency CASCADE", nativeQuery = true)
    void dropTable();

    @Modifying
    @Query(value = "TRUNCATE TABLE currency CASCADE" , nativeQuery = true)
    void truncateTable();
}
