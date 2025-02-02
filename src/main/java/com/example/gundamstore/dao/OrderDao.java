package com.example.gundamstore.dao;

import com.example.gundamstore.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class OrderDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Order> findAll() {
        String sql = "SELECT * FROM orders";
        return jdbcTemplate.query(sql, new OrderRowMapper());
    }

    public Order findById(int id) {
        String sql = "SELECT * FROM orders WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new OrderRowMapper(), id);
    }

    public int save(Order order) {
        String sql = "INSERT INTO orders (customer_id, order_date, total_amount) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, order.getCustomerId(), order.getOrderDate(), order.getTotalAmount());
    }

    public int update(Order order) {
        String sql = "UPDATE orders SET customer_id = ?, order_date = ?, total_amount = ? WHERE id = ?";
        return jdbcTemplate.update(sql, order.getCustomerId(), order.getOrderDate(), order.getTotalAmount(), order.getId());
    }

    public int deleteById(int id) {
        String sql = "DELETE FROM orders WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    private static final class OrderRowMapper implements RowMapper<Order> {
        @Override
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            Order order = new Order();
            order.setId(String.valueOf(rs.getInt("id")));
            order.setCustomerId(rs.getInt("customer_id"));
            order.setOrderDate(rs.getDate("order_date"));
            order.setTotalAmount(rs.getDouble("total_amount"));
            return order;
        }
    }
}
