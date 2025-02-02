package com.example.gundamstore.dao;

import com.example.gundamstore.model.Gundam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Nonnull;

@Repository
public class GundamDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public GundamDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Gundam> findAll() {
        String sql = "SELECT * FROM gundams";
        return jdbcTemplate.query(sql, new GundamRowMapper());
    }

    public Gundam findById(int id) {
        String sql = "SELECT * FROM gundams WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new GundamRowMapper(), id);
    }

    public int save(Gundam gundam) {
        String sql = "INSERT INTO gundams (name, model, price) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, gundam.getName(), gundam.getModel(), gundam.getPrice());
    }

    public int update(Gundam gundam) {
        String sql = "UPDATE gundams SET name = ?, model = ?, price = ? WHERE id = ?";
        return jdbcTemplate.update(sql, gundam.getName(), gundam.getModel(), gundam.getPrice(), gundam.getId());
    }

    public int deleteById(int id) {
        String sql = "DELETE FROM gundams WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    private static final class GundamRowMapper implements RowMapper<Gundam> {
        @Override
        public Gundam mapRow(ResultSet rs, int rowNum) throws SQLException {
            Gundam gundam = new Gundam();
            gundam.setId(String.valueOf(rs.getInt("id"))); // Convert int to String
            gundam.setName(rs.getString("name"));
            gundam.setModel(rs.getString("model"));
            gundam.setPrice(rs.getDouble("price"));
            return gundam;
        }
    }
}
