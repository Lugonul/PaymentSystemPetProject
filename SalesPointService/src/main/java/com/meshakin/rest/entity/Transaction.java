package com.meshakin.rest.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "transaction")
public class Transaction{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @EqualsAndHashCode.Include
    private LocalDate transactionDate;
    @EqualsAndHashCode.Include
    private BigDecimal sum;
    @EqualsAndHashCode.Include
    private String transactionName;
    @ManyToOne
    private Account account;
    @ManyToOne
    private TransactionType transactionType;
    @ManyToOne
    private Card card;
    @ManyToOne
    private Terminal terminal;
    @ManyToOne
    private ResponseCode responseCode;
    @Column(length = 6)
    @EqualsAndHashCode.Include
    private String authorizationCode;
    @EqualsAndHashCode.Include
    private LocalDateTime receivedFromIssuingBank;
    @EqualsAndHashCode.Include
    private LocalDateTime sendToIssuingBank;
    @Version
    Long version;
}
