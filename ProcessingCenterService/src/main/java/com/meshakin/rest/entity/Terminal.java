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

@Data
@EqualsAndHashCode(exclude = {"mcc","pos"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Terminal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 9)
    private String terminalId;
    @ManyToOne(fetch = FetchType.LAZY)
    private MerchantCategoryCode mcc;
    @ManyToOne(fetch = FetchType.LAZY)
    private SalesPoint pos;
    @Version
    private Long version;
}
