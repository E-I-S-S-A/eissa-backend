package com.eissa.backend.accounts.services;

import com.eissa.backend.accounts.classes.entities.Otp;
import com.eissa.backend.accounts.repos.OtpRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;

@Service
public class OtpService {

    public final int EXPIERY_MINUTES = 10;

    @Autowired
    OtpRepo otpRepo;

    public String generateOtp(){
        String otp = new DecimalFormat("000000").format(Math.random()*100000);
        return otp;
    }

    public int sendOtp(Otp otp){
        LocalDateTime localDateTime10 = LocalDateTime.now().plusMinutes(EXPIERY_MINUTES);
        otp.setExpiry(localDateTime10);
        int rows = otpRepo.insertOtp(otp);
        return rows;
    }

    public HttpStatus veriFyOtp(Otp otp){
        Otp otpFromDb = otpRepo.getOtp(otp);
        return HttpStatus.OK;
    }
}
