package com.meshakin.rest.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(length = 50)
    @EqualsAndHashCode.Include
    private String cardNumber;
    @EqualsAndHashCode.Include
    private LocalDate expirationDate;
    @EqualsAndHashCode.Include
    private String holderName;
    @ManyToOne(fetch = FetchType.LAZY)
    private CardStatus cardStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    private PaymentSystem paymentSystem;
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;
    @EqualsAndHashCode.Include
    private LocalDateTime receivedFromIssuingBank;
    @EqualsAndHashCode.Include
    private LocalDateTime sendToIssuingBank;
    @Version
    Long version;
}
