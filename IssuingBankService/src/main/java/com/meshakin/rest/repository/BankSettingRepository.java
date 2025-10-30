package com.meshakin.rest.repository;

import com.meshakin.rest.entity.BankSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BankSettingRepository extends JpaRepository<BankSetting,Long> {
    @Modifying
    @Query(value = "DROP TABLE IF EXISTS bank_setting CASCADE", nativeQuery = true)
    void dropTable();
}