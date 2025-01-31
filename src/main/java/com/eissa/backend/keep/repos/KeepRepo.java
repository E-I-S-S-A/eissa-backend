package com.eissa.backend.keep.repos;

import com.eissa.backend.keep.models.classes.entities.Keep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class KeepRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public int addKeep(Keep keep) {
        String query = "insert into keep(keepId, userId, title, description, backgroundColor) values(?,?,?,?,?)";
        return jdbcTemplate.update(query, keep.getKeepId(), keep.getUserId(), keep.getTitle(), keep.getDescription(), keep.getBackgroundColor());
    }

    public List<Keep> getKeeps(int page, int limit, String searchToken, String userId) {
        String query = "SELECT keepId, title, description, backgroundColor, createdOn FROM keep WHERE userId = ? ";

        if (searchToken != null && !searchToken.isEmpty()) {
            query += "AND (title LIKE ? OR description LIKE ?) ";
        }

        query += "ORDER BY createdOn DESC LIMIT ? OFFSET ?";

        int offset = (page - 1) * limit;

        if (searchToken != null && !searchToken.isEmpty()) {
            return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Keep.class), userId, "%" + searchToken + "%", "%" + searchToken + "%", limit, offset);
        } else {
            return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Keep.class), userId, limit, offset);
        }
    }


    public int updateKeep(Keep keep) {
        String query = "UPDATE keep SET title = ?, description = ?, backgroundColor = ? WHERE keepId = ? AND userId = ?";
        return jdbcTemplate.update(query, keep.getTitle(), keep.getDescription(), keep.getBackgroundColor(), keep.getKeepId(), keep.getUserId());
    }

    public int deleteKeep(String keepId, String userId) {
        String query = "DELETE FROM keep WHERE keepId = ? AND userId = ?";
        return jdbcTemplate.update(query, keepId, userId);
    }

}
