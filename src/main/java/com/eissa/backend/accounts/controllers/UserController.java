package com.eissa.backend.accounts.controllers;

import com.eissa.backend.accounts.models.classes.entities.Otp;
import com.eissa.backend.accounts.models.classes.entities.User;
import com.eissa.backend.accounts.models.classes.requests.EmailRequest;
import com.eissa.backend.accounts.repos.OtpRepo;
import com.eissa.backend.accounts.repos.UserRepo;
import com.eissa.backend.accounts.services.OtpService;
import com.eissa.backend.accounts.models.enums.CookieEnum;
import com.eissa.backend.common.utils.CookieUtil;
import com.eissa.backend.common.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/accounts")
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
            boolean isExists = userRepo.checkIfEmailExists(user.getEmail());
            if (isExists) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
            }
            int rows = userRepo.insertUser(user);
            if (rows == 1) {
                return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/email-exists")
    public ResponseEntity<String> checkIfEmailExists(@RequestParam String email) {
        try {
            boolean isExists = userRepo.checkIfEmailExists(email);
            if (isExists) {
                return ResponseEntity.status(HttpStatus.OK).body("Email exists");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/userId-exists")
    public ResponseEntity<String> checkIfUserIdExists(@RequestParam String userId) {
        try {
            boolean isExists = userRepo.checkIfUserIdExists(userId);
            if (isExists) {
                return ResponseEntity.status(HttpStatus.OK).body("userId exists");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("userId not found");
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
    public ResponseEntity<String> signin(HttpServletResponse response, @RequestBody User user) {
        try {
            User userFromDb = userRepo.getUserFromEmailPassword(user);

            if (userFromDb != null) {
                String accessToken = JwtUtil.generateToken(userFromDb.toString());
                CookieUtil.setCookie(response, CookieEnum.ACCESS_TOKEN, accessToken);
                return ResponseEntity.status(HttpStatus.OK).body("Success");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/forgot-password")
    public String forgotPassword(@RequestBody String requestBody) {
        return "hello";
    }


    @GetMapping("/eissa")
    public String eissa() {
        return "This is eissa, App is working fine. and this is new";
    }
}
