package com.eissa.backend.accounts.repos;

import com.eissa.backend.accounts.classes.entities.Otp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public class OtpRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public int insertOtp(Otp otp) {
        String query = "insert into otp (otp, email, expiry) values(?,?,?)";
        Date date10 = Date.valueOf(otp.getExpiry().toLocalDate());
        return jdbcTemplate.update(query, otp.getOtp(), otp.getEmail(), date10);
    }

    public Otp getOtp(Otp otp) {
        String query = "select * from otp where email=? and otp=?";
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{otp.getEmail(), otp.getOtp()}, Otp.class);

        } catch (Exception e) {
            return null;
        }
    }

    public int deleteOtp(String email) {
        String query = "delete * from otp where email=?";
        return jdbcTemplate.update(query, email);
    }
}
