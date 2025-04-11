package com.razorpay.paymentGateway.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RazorpayService {

    @Value("${razorpay.api.key}")
    private String apiKey;

    @Value("${razorpay.api.secret}")
    private String apiSecret ;

    /**
     * Creates an order in Razorpay.
     *
     * @param amount   The amount to be charged (in paise).
     * @param currency The currency code (e.g., "INR").
     * @param receipt  The receipt ID.
     * @param notes    Additional notes as key-value pairs.
     * @return The order ID as a string.
     * @throws RazorpayException If there is an error while creating the order.
     */
    public String createOrder(int amount, String currency, String receipt, Map<String, String> notes) throws RazorpayException {
        RazorpayClient razorpayClient = new RazorpayClient(apiKey, apiSecret);

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount);
        orderRequest.put("currency", currency);
        orderRequest.put("receipt", receipt);

        // Add notes if provided
        if (notes != null && !notes.isEmpty()) {
            JSONObject notesObject = new JSONObject();
            for (Map.Entry<String, String> entry : notes.entrySet()) {
                notesObject.put(entry.getKey(), entry.getValue());
            }
            orderRequest.put("notes", notesObject);
        }

        Order order = razorpayClient.orders.create(orderRequest);
        return order.toString();
    }
}
