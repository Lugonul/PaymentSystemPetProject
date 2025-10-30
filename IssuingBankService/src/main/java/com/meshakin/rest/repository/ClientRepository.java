package com.meshakin.rest.repository;

import com.meshakin.rest.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
    @Modifying
    @Query(value = "DROP TABLE IF EXISTS client CASCADE", nativeQuery = true)
    void dropTable();
}