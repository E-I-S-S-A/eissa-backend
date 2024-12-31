package com.eissa.backend.accounts.controllers;

import com.eissa.backend.accounts.classes.entities.Otp;
import com.eissa.backend.accounts.classes.entities.User;
import com.eissa.backend.accounts.classes.requests.EmailRequest;
import com.eissa.backend.accounts.repos.OtpRepo;
import com.eissa.backend.accounts.repos.UserRepo;
import com.eissa.backend.accounts.services.OtpService;
import common.pojos.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins  = "http://localhost:3000")
public class UserController {

    @Autowired
    UserRepo userRepo;
    @Autowired
    OtpService otpService;
    @Autowired
    private OtpRepo otpRepo;

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
                return ResponseEntity.status(HttpStatus.OK).body("Email exists");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
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
            Otp otpFromDb = otpService.veriFyOtp(otp);
            if (otpFromDb == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP");
            }

            if (otpFromDb.getExpiry().isAfter(LocalDateTime.now())) {
                try {
                    otpRepo.deleteAllOtpsByEmail(otp.getEmail());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                return ResponseEntity.status(HttpStatus.OK).body("OTP verification successful");
            } else {
                return ResponseEntity.status(HttpStatus.GONE).body("Expired OTP");
            }
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
