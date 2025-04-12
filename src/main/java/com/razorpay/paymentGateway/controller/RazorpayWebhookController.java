package com.razorpay.paymentGateway.controller;

import com.razorpay.paymentGateway.service.RazorpayWebhookService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.util.Base64;

@RestController
@RequestMapping("/webhook")
public class RazorpayWebhookController {

    @Value("${razorpay.webhook.secret}")
    private String webhookSecret;

    private final RazorpayWebhookService webhookService;

    public RazorpayWebhookController(RazorpayWebhookService webhookService) {
        this.webhookService = webhookService;
    }

    @PostMapping("/razorpay")
    public ResponseEntity<String> handleWebhook(HttpServletRequest request,
                                                @RequestHeader("X-Razorpay-Signature") String razorpaySignature) {
        try {
            StringBuilder payloadBuilder = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                payloadBuilder.append(line);
            }

            String payload = payloadBuilder.toString();

            // Verify Signature
            if (!verifySignature(payload, razorpaySignature, webhookSecret)) {
                return ResponseEntity.status(400).body("Invalid signature");
            }

            // Handle Webhook Event
            webhookService.processWebhook(payload);

            return ResponseEntity.ok("Webhook processed");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error processing webhook");
        }
    }

    private boolean verifySignature(String payload, String razorpaySignature, String secret) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        mac.init(secretKey);
        byte[] hash = mac.doFinal(payload.getBytes());
        String generatedSignature = Base64.getEncoder().encodeToString(hash);
        return generatedSignature.equals(razorpaySignature);
    }
}
