package com.meshakin.rest.repository;

import com.meshakin.rest.entity.CardStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CardStatusRepository extends JpaRepository<CardStatus,Long> {
    @Modifying
    @Query(value = "DROP TABLE IF EXISTS card_status CASCADE", nativeQuery = true)
    void dropTable();

    @Modifying
    @Query(value = "TRUNCATE TABLE card_status CASCADE" , nativeQuery = true)
    void truncateTable();
}
