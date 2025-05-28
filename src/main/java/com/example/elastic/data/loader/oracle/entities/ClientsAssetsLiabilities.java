package com.example.elastic.data.loader.oracle.entities;

import lombok.Data;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "bank_clients_assets_liabilities")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientsAssetsLiabilities {

    @Id
    @Column(name = "client_id")
    private Integer clientId;

    @Column(name = "cash_balance")
    private BigDecimal cashBalance;

    @Column(name = "savings_balance")
    private BigDecimal savingsBalance;

    @Column(name = "checking_balance")
    private BigDecimal checkingBalance;

    @Column(name = "fixed_deposits")
    private BigDecimal fixedDeposits;

    @Column(name = "stocks_value")
    private BigDecimal stocksValue;

    @Column(name = "bonds_value")
    private BigDecimal bondsValue;

    @Column(name = "mutual_funds_value")
    private BigDecimal mutualFundsValue;

    @Column(name = "real_estate_value")
    private BigDecimal realEstateValue;

    @Column(name = "vehicles_value")
    private BigDecimal vehiclesValue;

    @Column(name = "gold_value")
    private BigDecimal goldValue;

    @Column(name = "credit_card_debt")
    private BigDecimal creditCardDebt;

    @Column(name = "home_loan_balance")
    private BigDecimal homeLoanBalance;

    @Column(name = "car_loan_balance")
    private BigDecimal carLoanBalance;

    @Column(name = "education_loan")
    private BigDecimal educationLoan;

    @Column(name = "personal_loan")
    private BigDecimal personalLoan;

    @Column(name = "other_liabilities")
    private BigDecimal otherLiabilities;

    @Column(name = "monthly_income")
    private BigDecimal monthlyIncome;

    @Column(name = "monthly_expenses")
    private BigDecimal monthlyExpenses;

    @Column(name = "net_worth")
    private BigDecimal netWorth;
}