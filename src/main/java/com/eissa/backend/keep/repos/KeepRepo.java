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
        String query = "SELECT keepId, title, description, backgroundColor, createdOn " + "FROM keep WHERE userId = ? ORDER BY createdOn DESC LIMIT ? OFFSET ?";

        int offset = (page - 1) * limit;

        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Keep.class), userId, limit, offset);
    }
}
