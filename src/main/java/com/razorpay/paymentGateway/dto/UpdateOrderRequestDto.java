package com.razorpay.paymentGateway.dto;

import java.util.Map;

public class UpdateOrderRequestDto {
    private Map<String, String> notes;

    public Map<String, String> getNotes() {
        return notes;
    }

    public void setNotes(Map<String, String> notes) {
        this.notes = notes;
    }
}
