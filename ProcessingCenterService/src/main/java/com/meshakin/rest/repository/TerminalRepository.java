package com.meshakin.rest.repository;

import com.meshakin.rest.entity.Terminal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TerminalRepository extends JpaRepository<Terminal,Long> {
    @Modifying
    @Query(value = "DROP TABLE IF EXISTS terminal CASCADE", nativeQuery = true)
    void dropTable();
}