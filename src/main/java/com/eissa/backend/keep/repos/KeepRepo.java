package com.eissa.backend.keep.repos;

import com.eissa.backend.keep.models.classes.entities.Keep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class KeepRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public int addKeep(Keep keep){
        String query = "insert into keep(keepId, title, description,backgroundColor) values(?,?,?,?)";
        return jdbcTemplate.update(query, keep.getKeepId(),  keep.getTitle(), keep.getDescription(),keep.getBackgroundColor());
    }
}
