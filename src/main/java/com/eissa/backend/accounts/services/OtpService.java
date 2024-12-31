package com.eissa.backend.accounts.services;

import com.eissa.backend.accounts.classes.entities.Otp;
import com.eissa.backend.accounts.repos.OtpRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;

@Service
public class OtpService {

    public final int EXPIERY_MINUTES = 10;
    public final String subject = "OTP Verification for E.I.S.S.A";
    public final String body = "Your otp is ";

    @Autowired
    OtpRepo otpRepo;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") private String sender;

    public String generateOtp() {
        String otp = new DecimalFormat("000000").format(Math.random() * 100000);
        return otp;
    }

    public int sendOtp(Otp otp) {

        boolean isEmailSent = this.sendEmail(otp.getEmail(), subject, body+otp.getOtp());
        if(!isEmailSent){
            return 0;
        }
        LocalDateTime localDateTime10 = LocalDateTime.now().plusMinutes(EXPIERY_MINUTES);
        otp.setExpiry(localDateTime10);
        int rows = otpRepo.insertOtp(otp);
        return rows;
    }

    public Otp veriFyOtp(Otp otp) {
        Otp otpFromDb = otpRepo.getOtp(otp);
        return otpFromDb;
    }

    public boolean sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(to);
            mailMessage.setText(body);
            mailMessage.setSubject(subject);

            javaMailSender.send(mailMessage);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
