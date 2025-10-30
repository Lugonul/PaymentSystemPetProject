package com.meshakin.rest.entity;


import jakarta.persistence.Column;
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
@EqualsAndHashCode(exclude = {"currency","accountType","client"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String accountNumber;

    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.LAZY)
    private Currency currency;

    @ManyToOne(fetch = FetchType.LAZY)
    private AccountType accountType;

    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;

    private LocalDate accountOpeningDate;

    private Boolean suspendingOperation;

    private LocalDate accountClosedDate;

    @Version
    private Long version;
}
