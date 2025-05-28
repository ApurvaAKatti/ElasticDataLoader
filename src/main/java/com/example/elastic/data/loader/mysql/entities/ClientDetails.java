package com.example.elastic.data.loader.mysql.entities;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "bank_clients", schema = "bank_details")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Integer clientId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    // @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private String gender;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "country")
    private String country;

    // @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private String accountType;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "account_open_date")
    private Date accountOpenDate;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "credit_score")
    private Integer creditScore;

    @Column(name = "employment_status")
    private String employmentStatus;

    @Column(name = "income")
    private BigDecimal income;

    // @Enumerated(EnumType.STRING)
    @Column(name = "relationship_status")
    private String relationshipStatus;

    // // Enums
    // public enum Gender {
    //     MALE, FEMALE, OTHER
    // }

    // public enum AccountType {
    //     SAVINGS, CURRENT, FIXED DEPOSIT
    // }

    // public enum RelationshipStatus {
    //     NEW, REGULAR, VIP
    // }
}
