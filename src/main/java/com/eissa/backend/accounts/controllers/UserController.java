package com.eissa.backend.accounts.controllers;
import com.eissa.backend.accounts.entities.User;
import com.eissa.backend.accounts.repos.UserRepo;
import common.pojos.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class UserController {

    @Autowired
    UserRepo userRepo;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user){
        try {
            boolean isExists = userRepo.isEmailExists(user.getEmail());
            if(isExists){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
            }
            int rows = userRepo.insertUser(user);
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

//    @PostMapping("/send-otp")
//    public ResponseEntity<String> sendOtp(@RequestBody String email){
//        try{
//
//        }
//        catch(Exception e){
//
//        }
//    }

    @PostMapping("/signin")
    public Result signin(@RequestBody String requestBody){
        return new Result(0, "Signin "+requestBody);
    }

    @PutMapping("/forgot-password")
    public Result forgotPassword(@RequestBody String requestBody){
        return new Result(0, "Forgot "+requestBody);
    }


    @GetMapping("/eissa")
    public String eissa(){
        return "This is eissa, App is working fine. and this is new";
    }
}
