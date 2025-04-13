package com.razorpay.paymentGateway.controller;

import com.razorpay.paymentGateway.dto.OrderRequestDto;
import com.razorpay.paymentGateway.dto.UpdateOrderRequestDto;
import com.razorpay.paymentGateway.model.RazorpayOrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.razorpay.paymentGateway.service.RazorpayService;

@RestController
@RequestMapping("/razorpay")
@CrossOrigin(origins = "http://localhost:63342")
public class RazorpayController {

     @Autowired
     private  RazorpayService razorpayService;

    @PostMapping("/createOrder")
    public ResponseEntity<RazorpayOrderResponse> createOrder(@RequestBody OrderRequestDto requestDto) {
        try {
            RazorpayOrderResponse response = razorpayService.createOrder(
                    requestDto.getAmount(),
                    requestDto.getCurrency(),
                    requestDto.getReceipt(),
                    requestDto.getNotes()
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/fetchOrder/{orderId}")
    public String fetchOrder(@PathVariable String orderId) {
        try {
            return razorpayService.fetchOrder(orderId);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching order";
        }
    }

    @GetMapping("/fetchAllOrders")
    public String fetchAllOrders(@RequestParam(defaultValue = "10") int count,
                                 @RequestParam(defaultValue = "0") int skip) {
        try {
            return razorpayService.fetchAllOrders(count, skip);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching all orders";
        }
    }


    @PatchMapping("/updateOrder/{orderId}")
    public String updateOrder(@PathVariable String orderId, @RequestBody UpdateOrderRequestDto request) {
        try {
            return razorpayService.updateOrder(orderId, request.getNotes());
        } catch (Exception e) {
            e.printStackTrace();
            return "Error updating order";
        }
    }



}
