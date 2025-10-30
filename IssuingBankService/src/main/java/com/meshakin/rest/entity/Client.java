package com.meshakin.rest.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String lastName;

    @Column(length = 100)
    private String firstName;

    @Column(length = 100)
    private String middleName;

    private LocalDate birthDate;

    private String document;

    private String address;

    @Column(length = 20)
    private String phone;

    private String email;

    @Version
    private Long version;
}
