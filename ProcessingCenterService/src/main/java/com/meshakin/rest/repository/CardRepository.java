package com.meshakin.rest.repository;

import com.meshakin.rest.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card,Long> {
    @Modifying
    @Query(value = "DROP TABLE IF EXISTS card CASCADE", nativeQuery = true)
    void dropTable();
}
