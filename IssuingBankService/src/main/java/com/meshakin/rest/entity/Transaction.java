package com.meshakin.rest.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(exclude = {"transactionType","account"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate transactionDate;

    private BigDecimal sum;

    private String transactionName;

    @ManyToOne(fetch = FetchType.LAZY)
    private TransactionType transactionType;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    private LocalDateTime sentToProcessingCenter;

    private LocalDateTime receivedFromProcessingCenterDate;

    @Version
    private Long version;
}
