package com.razorpay.paymentGateway.dto;

import java.util.Map;

/**
 * OrderRequestDto is a Data Transfer Object (DTO) that represents the request
 * body for creating an order in the Razorpay payment gateway.
 * It contains fields for amount, currency, receipt, and notes.
 */
public class OrderRequestDto {

    private int amount;                   // Amount in paise
    private String currency;              // INR, USD, etc.
    private String receipt;               // Receipt ID
    private Map<String, String> notes;    // Notes as key-value pairs

    // Getters and Setters

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public Map<String, String> getNotes() {
        return notes;
    }

    public void setNotes(Map<String, String> notes) {
        this.notes = notes;
    }
}
