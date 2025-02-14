package com.example.gundamstore.dao;

import com.example.gundamstore.model.Gundam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class GundamDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Gundam> findAll() {
        String sql = "SELECT * FROM gundams";
        return jdbcTemplate.query(sql, new GundamRowMapper());
    }

    public Gundam findById(int id) {
        String sql = "SELECT * FROM gundams WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new GundamRowMapper(), id);
    }

    public int save(Gundam gundam) {
        String sql = "INSERT INTO gundams (name, model, price, series, stock, image_url, description) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, gundam.getName(), gundam.getModel(), gundam.getPrice(), gundam.getSeries(), gundam.getStock(), gundam.getImageUrl(), gundam.getDescription());
    }

    public int update(Gundam gundam) {
        String sql = "UPDATE gundams SET name = ?, model = ?, price = ?, series = ?, stock = ?, image_url = ?, description = ? WHERE id = ?";
        return jdbcTemplate.update(sql, gundam.getName(), gundam.getModel(), gundam.getPrice(), gundam.getSeries(), gundam.getStock(), gundam.getImageUrl(), gundam.getDescription(), gundam.getId());
    }

    public int deleteById(int id) {
        String sql = "DELETE FROM gundams WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public List<Gundam> search(String keyword) {
        String sql = "SELECT * FROM gundams WHERE name LIKE ? OR model LIKE ? OR series LIKE ? OR description LIKE ?";
        String searchKeyword = "%" + keyword + "%";
        return jdbcTemplate.query(sql, new GundamRowMapper(), searchKeyword, searchKeyword, searchKeyword, searchKeyword);
    }

    private static final class GundamRowMapper implements RowMapper<Gundam> {
        @Override
        public Gundam mapRow(ResultSet rs, int rowNum) throws SQLException {
            Gundam gundam = new Gundam();
            gundam.setId(String.valueOf(rs.getInt("id")));
            gundam.setName(rs.getString("name"));
            gundam.setModel(rs.getString("model"));
            gundam.setPrice(rs.getDouble("price"));
            gundam.setSeries(rs.getString("series"));
            gundam.setStock(rs.getInt("stock"));
            gundam.setImageUrl(rs.getString("image_url"));
            gundam.setDescription(rs.getString("description")); // Ánh xạ thuộc tính description
            return gundam;
        }
    }
}