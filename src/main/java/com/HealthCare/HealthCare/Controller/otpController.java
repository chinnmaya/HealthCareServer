package com.HealthCare.HealthCare.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping
public class otpController {
    @Autowired
    private JavaMailSender javaMailSender;

    private int otpCode;

    @GetMapping("/otp")
    public String showOtpForm() {
        return "otp";
    }

    @PostMapping("/otp-verify")
    public String verifyOtp(@RequestParam("code") int code) {
        if (code == otpCode) {
            //model.addAttribute("message", "OTP verified successfully!");
            return "success";
        } else {
           // model.addAttribute("error", "Invalid OTP!");
            return "Invalid otp";
        }
    }

    @PostMapping("/send-otp")
    public String sendOtp(@RequestParam("email") String email) {
        try {
            // Generate OTP
            otpCode = new Random().nextInt(900000) + 100000;

            // Send email
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(email);
            msg.setSubject("Your OTP Code");
            msg.setText("Your OTP code is: " + otpCode);

            javaMailSender.send(msg);
            return "Otp was Sent to your Email.";
        }catch (Exception ex){
            return "Something Went Wrong !! try again later.";
        }

        //model.addAttribute("message", "OTP sent successfully!");

    }
}
