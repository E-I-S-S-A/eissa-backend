package com.eissa.backend.accounts.repos;

import com.eissa.backend.accounts.models.classes.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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

    public boolean checkIfEmailExists(String email) {
        String query = "select count(*) from users where email = ?";
        Integer count = jdbcTemplate.queryForObject(query, new Object[]{email}, Integer.class);
        return count != null && count > 0;
    }

    public boolean checkIfUserIdExists(String userId) {
        String query = "select count(*) from users where userId = ?";
        Integer count = jdbcTemplate.queryForObject(query, new Object[]{userId}, Integer.class);
        return count != null && count > 0;
    }

    public User getUserFromEmailPassword(User user) {
        try {
            String query = "select * from users where email = ? and password = ?";
            return jdbcTemplate.queryForObject(
                    query,
                    new Object[]{user.getEmail(), user.getPassword()},
                    new BeanPropertyRowMapper<>(User.class)
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}
