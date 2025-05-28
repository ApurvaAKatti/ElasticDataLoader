package com.example.elastic.data.loader.elasticsearch.document;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Document(indexName = "client-data")
@Schema(description = "Client Full Profile")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientFullProfile {

    // ClientDetails fields
    @Schema(description = "Client unique identifier")
    private Integer clientId;

    @Schema(description = "First name")
    private String firstName;

    @Schema(description = "Last name")
    private String lastName;

    @Schema(description = "Date of birth")
    private Date dateOfBirth;

    @Schema(description = "Gender")
    private String gender;

    @Schema(description = "Email address")
    private String email;

    @Schema(description = "Phone number")
    private String phone;

    @Schema(description = "Address")
    private String address;

    @Schema(description = "City")
    private String city;

    @Schema(description = "State")
    private String state;

    @Schema(description = "Zip code")
    private String zipCode;

    @Schema(description = "Country")
    private String country;

    @Schema(description = "Account type")
    private String accountType;

    @Schema(description = "Account number")
    private String accountNumber;

    @Schema(description = "Account open date")
    private Date accountOpenDate;

    @Schema(description = "Account balance")
    private BigDecimal balance;

    @Schema(description = "Credit score")
    private Integer creditScore;

    @Schema(description = "Employment status")
    private String employmentStatus;

    @Schema(description = "Income")
    private BigDecimal income;

    @Schema(description = "Relationship status")
    private String relationshipStatus;

    // ClientsAssetsLiabilities fields
    @Schema(description = "Cash balance")
    private BigDecimal cashBalance;

    @Schema(description = "Savings balance")
    private BigDecimal savingsBalance;

    @Schema(description = "Checking balance")
    private BigDecimal checkingBalance;

    @Schema(description = "Fixed deposits")
    private BigDecimal fixedDeposits;

    @Schema(description = "Stocks value")
    private BigDecimal stocksValue;

    @Schema(description = "Bonds value")
    private BigDecimal bondsValue;

    @Schema(description = "Mutual funds value")
    private BigDecimal mutualFundsValue;

    @Schema(description = "Real estate value")
    private BigDecimal realEstateValue;

    @Schema(description = "Vehicles value")
    private BigDecimal vehiclesValue;

    @Schema(description = "Gold value")
    private BigDecimal goldValue;

    @Schema(description = "Credit card debt")
    private BigDecimal creditCardDebt;

    @Schema(description = "Home loan balance")
    private BigDecimal homeLoanBalance;

    @Schema(description = "Car loan balance")
    private BigDecimal carLoanBalance;

    @Schema(description = "Education loan")
    private BigDecimal educationLoan;

    @Schema(description = "Personal loan")
    private BigDecimal personalLoan;

    @Schema(description = "Other liabilities")
    private BigDecimal otherLiabilities;

    @Schema(description = "Monthly income")
    private BigDecimal monthlyIncome;

    @Schema(description = "Monthly expenses")
    private BigDecimal monthlyExpenses;

    @Schema(description = "Net worth")
    private BigDecimal netWorth;
}