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

@Data
@EqualsAndHashCode(exclude = {"transactionType","card","terminal","responseCode"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate transactionDate;
    private BigDecimal sum;
    @ManyToOne(fetch = FetchType.LAZY)
    private TransactionType transactionType;
    @ManyToOne(fetch = FetchType.LAZY)
    private Card card;
    @ManyToOne(fetch = FetchType.LAZY)
    private Terminal terminal;
    @ManyToOne(fetch = FetchType.LAZY)
    private ResponseCode responseCode;
    private String authorizationCode;
    @Version
    private Long version;
}
