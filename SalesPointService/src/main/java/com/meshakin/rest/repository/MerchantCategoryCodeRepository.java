package com.meshakin.rest.repository;

import com.meshakin.rest.entity.MerchantCategoryCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantCategoryCodeRepository extends JpaRepository<MerchantCategoryCode,Long> {
    @Modifying
    @Query(value = "DROP TABLE IF EXISTS merchant_category_code CASCADE", nativeQuery = true)
    void dropTable();

    @Modifying
    @Query(value = "TRUNCATE TABLE merchant_category_code CASCADE" , nativeQuery = true)
    void truncateTable();
}
