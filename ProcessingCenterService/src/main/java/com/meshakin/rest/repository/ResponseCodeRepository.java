package com.meshakin.rest.repository;


import com.meshakin.rest.entity.ResponseCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseCodeRepository extends JpaRepository<ResponseCode, Long> {
    @Modifying
    @Query(value = "DROP TABLE IF EXISTS response_code CASCADE", nativeQuery = true)
    void dropTable();
}