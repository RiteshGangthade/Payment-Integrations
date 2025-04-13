package com.razorpay.paymentGateway.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RazorpayOrderResponse {
    private String orderId;
    private int amount;
    private String currency;
    private String razorpayKey;
}
