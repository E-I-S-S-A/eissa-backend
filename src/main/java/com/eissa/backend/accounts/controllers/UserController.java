package com.eissa.backend.accounts.controllers;

import com.eissa.backend.accounts.classes.entities.Otp;
import com.eissa.backend.accounts.classes.entities.User;
import com.eissa.backend.accounts.classes.requests.EmailRequest;
import com.eissa.backend.accounts.repos.UserRepo;
import com.eissa.backend.accounts.services.OtpService;
import common.pojos.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class UserController {

    @Autowired
    UserRepo userRepo;
    @Autowired
    OtpService otpService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        try {
            boolean isExists = userRepo.isEmailExists(user.getEmail());
            if (isExists) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
            }
            int rows = userRepo.insertUser(user);
            if (rows == 1) {
                return ResponseEntity.status(HttpStatus.CREATED).build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/email-exists")
    public ResponseEntity<String> isEmailExists(@RequestParam String email) {
        try {
            boolean isExists = userRepo.isEmailExists(email);
            if (isExists) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestBody EmailRequest emailRequest) {
        try {
            String otpString = otpService.generateOtp();
            Otp otp = new Otp(emailRequest.getEmail(), otpString);
            int rows = otpService.sendOtp(otp);
            if (rows == 1) {
                return ResponseEntity.status(HttpStatus.CREATED).build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody Otp otp) {
        try {
            HttpStatus status = otpService.veriFyOtp(otp);
            return ResponseEntity.status(status).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/signin")
    public Result signin(@RequestBody String requestBody) {
        return new Result(0, "Signin " + requestBody);
    }

    @PutMapping("/forgot-password")
    public Result forgotPassword(@RequestBody String requestBody) {
        return new Result(0, "Forgot " + requestBody);
    }


    @GetMapping("/eissa")
    public String eissa() {
        return "This is eissa, App is working fine. and this is new";
    }
}
