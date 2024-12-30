package com.eissa.backend.accounts.classes.entities;

import java.time.LocalDateTime;

public class Otp {
    Long otpId;
    String email;
    String otp;
    LocalDateTime expiry;

    public Otp() {
    }

    public Otp(String email, String otp) {
        this.email = email;
        this.otp = otp;
    }

    public Long getOtpId() {
        return otpId;
    }

    public void setOtpId(Long otpId) {
        this.otpId = otpId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public LocalDateTime getExpiry() {
        return expiry;
    }

    public void setExpiry(LocalDateTime expiry) {
        this.expiry = expiry;
    }
}
