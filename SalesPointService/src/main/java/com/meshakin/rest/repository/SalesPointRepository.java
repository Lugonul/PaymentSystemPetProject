package com.meshakin.rest.repository;

import com.meshakin.rest.entity.SalesPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesPointRepository extends JpaRepository<SalesPoint,Long> {
    @Modifying
    @Query(value = "DROP TABLE IF EXISTS sales_point CASCADE", nativeQuery = true)
    void dropTable();

    @Modifying
    @Query(value = "TRUNCATE TABLE sales_point CASCADE" , nativeQuery = true)
    void truncateTable();
}
