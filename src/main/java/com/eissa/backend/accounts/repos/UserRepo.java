package com.eissa.backend.accounts.repos;

import com.eissa.backend.accounts.classes.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int insertUser(User user) {
            String query = "insert into users (userId, firstName, lastName, email, password) values (?, ?, ?, ?, ?)";
            return jdbcTemplate.update(query, user.getUserId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
    }

    public boolean checkIfEmailExists(String email){
        String query = "select count(*) from users where email = ?";
        Integer count =  jdbcTemplate.queryForObject(query, new Object[]{email}, Integer.class);
        return count != null && count > 0;
    }

    public boolean checkIfUserIdExists(String userId){
        String query = "select count(*) from users where userId = ?";
        Integer count =  jdbcTemplate.queryForObject(query, new Object[]{userId}, Integer.class);
        return count != null && count > 0;
    }
}
