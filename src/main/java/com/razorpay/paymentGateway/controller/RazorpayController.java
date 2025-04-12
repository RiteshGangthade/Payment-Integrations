package com.razorpay.paymentGateway.controller;

import com.razorpay.paymentGateway.dto.OrderRequestDto;
import com.razorpay.paymentGateway.dto.UpdateOrderRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.razorpay.paymentGateway.service.RazorpayService;

@RestController
@RequestMapping("/razorpay")
@CrossOrigin(origins = "http://localhost:63342")
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
