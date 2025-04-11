package com.razorpay.paymentGateway.controller;

import com.razorpay.paymentGateway.dto.OrderRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.razorpay.paymentGateway.service.RazorpayService;

@RestController
@RequestMapping("/razorpay")
public class RazorpayController {

     @Autowired
     private  RazorpayService razorpayService;

    @PostMapping("/createOrder")
    public String createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        try {
            return razorpayService.createOrder(
                    orderRequestDto.getAmount(),
                    orderRequestDto.getCurrency(),
                    orderRequestDto.getReceipt(),
                    orderRequestDto.getNotes()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return "Error creating order";
        }
    }
}
