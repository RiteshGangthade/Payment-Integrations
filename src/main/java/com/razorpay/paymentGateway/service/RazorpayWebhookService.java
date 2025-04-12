package com.razorpay.paymentGateway.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class RazorpayWebhookService {

    public void processWebhook(String payload) {
        JSONObject webhookJson = new JSONObject(payload);
        String event = webhookJson.getString("event");

        switch (event) {
            case "payment.captured":
                handlePaymentCaptured(webhookJson);
                break;
            case "payment.failed":
                handlePaymentFailed(webhookJson);
                break;
            case "order.paid":
                handleOrderPaid(webhookJson);
                break;
            default:
                System.out.println("Unhandled event type: " + event);
        }
    }

    private void handlePaymentCaptured(JSONObject webhookJson) {
        JSONObject paymentEntity = webhookJson
                .getJSONObject("payload")
                .getJSONObject("payment")
                .getJSONObject("entity");

        String paymentId = paymentEntity.getString("id");
        String orderId = paymentEntity.optString("order_id");
        int amount = paymentEntity.getInt("amount");

        // TODO: Update payment status in your database
        System.out.println("✅ Payment Captured: " + paymentId + ", Amount: " + amount + ", Order: " + orderId);
    }

    private void handlePaymentFailed(JSONObject webhookJson) {
        JSONObject paymentEntity = webhookJson
                .getJSONObject("payload")
                .getJSONObject("payment")
                .getJSONObject("entity");

        String paymentId = paymentEntity.getString("id");
        String reason = paymentEntity.optString("error_description");

        // TODO: Log or update DB with failed status
        System.out.println("❌ Payment Failed: " + paymentId + ", Reason: " + reason);
    }

    private void handleOrderPaid(JSONObject webhookJson) {
        JSONObject orderEntity = webhookJson
                .getJSONObject("payload")
                .getJSONObject("order")
                .getJSONObject("entity");

        String orderId = orderEntity.getString("id");

        // TODO: Update order status in your DB
        System.out.println("🧾 Order Paid: " + orderId);
    }
}
