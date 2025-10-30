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

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(exclude = {"cardStatus","paymentSystem","account","client"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String cardNumber;

    private LocalDate expirationDate;

    private String holderName;

    @ManyToOne(fetch = FetchType.LAZY)
    private CardStatus cardStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private PaymentSystem paymentSystem;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;

    private LocalDateTime sentToProcessingCenter;

    private LocalDateTime receivedFromProcessingCenterDate;

    @Version
    private Long version;
}
