package com.example.gundamstore.dao;

import com.example.gundamstore.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CustomerDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Customer> findAll() {
        String sql = "SELECT * FROM customers";
        return jdbcTemplate.query(sql, new CustomerRowMapper());
    }

    public Customer findById(int id) {
        String sql = "SELECT * FROM customers WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new CustomerRowMapper(), id);
    }

    public int save(Customer customer) {
        String sql = "INSERT INTO customers (name, email, phone_number, address) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getPhoneNumber(), customer.getAddress());
    }

    public int update(Customer customer) {
        String sql = "UPDATE customers SET name = ?, email = ?, phone_number = ?, address = ? WHERE id = ?";
        return jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getPhoneNumber(), customer.getAddress(), customer.getId());
    }   

    public int deleteById(int id) {
        String sql = "DELETE FROM customers WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public List<Customer> search(String keyword) {
        String sql = "SELECT * FROM customers WHERE name LIKE ? OR email LIKE ? OR phone_number LIKE ? OR address LIKE ?";
        String searchKeyword = "%" + keyword + "%";
        return jdbcTemplate.query(sql, new CustomerRowMapper(), searchKeyword, searchKeyword, searchKeyword, searchKeyword);
    }

    private static final class CustomerRowMapper implements RowMapper<Customer> {
        @Override
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            Customer customer = new Customer();
            customer.setId(String.valueOf(rs.getInt("id")));
            customer.setName(rs.getString("name"));
            customer.setEmail(rs.getString("email"));
            customer.setPhoneNumber(rs.getString("phone_number"));
            customer.setAddress(rs.getString("address"));
            return customer;
        }
    }
}
