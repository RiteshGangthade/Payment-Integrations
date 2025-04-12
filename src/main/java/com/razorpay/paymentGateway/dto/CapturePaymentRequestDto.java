package com.razorpay.paymentGateway.dto;

public class CapturePaymentRequestDto {
    private int amount; // Amount in paise

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
