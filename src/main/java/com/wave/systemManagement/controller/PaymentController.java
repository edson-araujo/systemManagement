package com.wave.systemManagement.controller;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.wave.systemManagement.DTO.response.PaymentLinkResponse;
import com.wave.systemManagement.model.Plantype;
import com.wave.systemManagement.model.User;
import com.wave.systemManagement.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/payments")
public class PaymentController {
    @Value("${razorpay.api.key}")
    private String apiKey;
    @Value("razorpay.api.secret")
    private String apiSecret;

    @Autowired
    private UserService userService;

    @PostMapping("/{planType}")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(
            @PathVariable Plantype planType,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        int amount = 799*1000;
        if(planType.equals(Plantype.ANNUALLY)){
            amount = amount*12;
            amount = (int)(amount*0.7); // Giving 30% discount off i.e 30/100
        }
        try{
            RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecret);

            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", amount);
            paymentLinkRequest.put("currency", "NGN");

            JSONObject customer = new JSONObject();
            customer.put("name",user.getFullName());
            customer.put("email",user.getEmail());
            paymentLinkRequest.put("customer",customer);

            JSONObject notify = new JSONObject();
            notify.put("email", true);
            notify.put("Phone Number", true);
            paymentLinkRequest.put("notify", notify);

            paymentLinkRequest.put("callback_url",
                    "http://localhost:5173/upgrade_plan/success?planType" + planType);
            PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);
            String paymentLinkId = payment.get("id");
            String paymentLinkUrl = payment.get("short_url");

            PaymentLinkResponse response = new PaymentLinkResponse();
            response.setPayment_link_url(paymentLinkUrl);
            response.setPayment_link_id(paymentLinkId);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (RazorpayException e){
            throw new RazorpayException("Payment not successfully");
        }
    }
}