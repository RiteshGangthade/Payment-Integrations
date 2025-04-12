package com.razorpay.paymentGateway.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
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

    /**
     * Fetches an order from Razorpay using the order ID.
     *
     * @param orderId The ID of the order to fetch.
     * @return The order details as a string.
     * @throws RazorpayException If there is an error while fetching the order.
     */
    public String fetchOrder(String orderId) throws RazorpayException {
        RazorpayClient client = new RazorpayClient(apiKey, apiSecret);
        Order order = client.orders.fetch(orderId);
        return order.toString(); // Optional: return order.toJson().toString() for cleaner output
    }

    public String fetchAllOrders(int count, int skip) throws RazorpayException {
        RazorpayClient client = new RazorpayClient(apiKey, apiSecret);

        JSONObject jsonObject= new JSONObject();
        jsonObject.put("count", count);
        jsonObject.put("skip", skip);

        List<Order> orderList = client.orders.fetchAll(jsonObject);

        JSONArray jsonArray = new JSONArray();
        for (Order order : orderList) {
            jsonArray.put(order.toJson());
        }

        JSONObject result = new JSONObject();
        result.put("orders", jsonArray);
        result.put("count", jsonArray.length());

        return result.toString();
    }

    public String updateOrder(String orderId, Map<String, String> notes) throws RazorpayException {
        RazorpayClient client = new RazorpayClient(apiKey, apiSecret);

        JSONObject updateRequest = new JSONObject();
        JSONObject notesObject = new JSONObject();

        if (notes != null) {
            for (Map.Entry<String, String> entry : notes.entrySet()) {
                notesObject.put(entry.getKey(), entry.getValue());
            }
            updateRequest.put("notes", notesObject);
        }

        Order updatedOrder = client.orders.edit(orderId, updateRequest);
        return updatedOrder.toJson().toString();
    }


}
