package com.eissa.backend.accounts.repos;

import com.eissa.backend.accounts.classes.entities.Otp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OtpRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public int insertOtp(Otp otp) {
        String query = "insert into otp (otp, email, expiry) values(?,?,?)";
        return jdbcTemplate.update(query, otp.getOtp(), otp.getEmail(), otp.getExpiry());
    }

    public Otp getOtp(Otp otp) {
        String query = "select * from otp where email=? and otp=?";
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{otp.getEmail(), otp.getOtp()}, (rs, row)->{
                Otp result = new Otp();
                result.setEmail(rs.getString("email"));
                result.setOtp(rs.getString("otp"));
                result.setExpiry(rs.getTimestamp("expiry").toLocalDateTime());

                return result;
            });

        } catch (Exception e) {
            return null;
        }
    }

    public int deleteAllOtpsByEmail(String email) {
        String query = "delete from otp where email=?";
        return jdbcTemplate.update(query, email);
    }
}
