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
@EqualsAndHashCode(exclude = {"acquiringBank"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class SalesPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String posName;
    private String posAddress;
    @Column(length = 12)
    private String posInn;
    @ManyToOne(fetch = FetchType.LAZY)
    private AcquiringBank acquiringBank;
    @Version
    private Long version;
}
