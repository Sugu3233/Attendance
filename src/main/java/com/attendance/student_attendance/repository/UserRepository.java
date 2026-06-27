package com.attendance.student_attendance.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.attendance.student_attendance.model.User;

@Repository
public class UserRepository {

    private JdbcTemplate template;

    public JdbcTemplate getTemplate() {
        return template;
    }

    @Autowired
    public void setTemplate(JdbcTemplate jdbc) {
        this.template = jdbc;
    }

    public List<User> getAllUsers() {
        String sql = "select * from users where oprtnl_flag = true";
        return template.query(sql, commonMapper());
    }

    public User save(User user) {
        String sql = """
                insert into users(name, mobile_no, password, address, city, district, dob, role, oprtnl_flag)
                values(?,?,?,?,?,?,?,?,?)
                """;
        Object[] data = {
                user.getName(),
                user.getMobile_no(),
                user.getPassword(),
                user.getAddress(),
                user.getCity(),
                user.getDistrict(),
                user.getDob(),
                user.getRole(),
                user.getOprtnl_flag()
        };
        template.update(sql, data);
        return user;
    }

    public void saveAll(List<User> users) {

        String sql = """
                insert into users
                (name,mobile_no,password,address,city,district,dob,role,oprtnl_flag)
                values (?,?,?,?,?,?,?,?,?)
                """;

        for (User user : users) {

            Object[] data = {
                    user.getName(),
                    user.getMobile_no(),
                    user.getPassword(),
                    user.getAddress(),
                    user.getCity(),
                    user.getDistrict(),
                    user.getDob(),
                    user.getRole(),
                    user.getOprtnl_flag()
            };

            template.update(sql, data);
        }

    }

    private RowMapper<User> commonMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setMobile_no(rs.getString("mobile_no"));
            user.setAddress(rs.getString("address"));
            user.setCity(rs.getString("city"));
            user.setDistrict(rs.getString("district"));
            user.setDob(rs.getDate("dob").toLocalDate());
            user.setPassword(rs.getString("password"));
            user.setRole(rs.getString("role"));
            user.setOprtnl_flag(rs.getBoolean("oprtnl_flag"));
            return user;
        };
    }

    public User getUserById(Long id) {
        String sql = "select * from users where id = ?";
        return template.queryForObject(sql, commonMapper(), id);
    }

    public User updateUserById(Long id, User user) {
        String sql = """
                update users set
                name = ?,
                mobile_no=?,
                password=?,
                address=?,
                city=?,
                district=?,
                dob=?,
                role=?,
                oprtnl_flag=?
                """;
        Object[] data = {
                user.getName(),
                user.getMobile_no(),
                user.getPassword(),
                user.getAddress(),
                user.getCity(),
                user.getDistrict(),
                user.getDob(),
                user.getRole(),
                user.getOprtnl_flag()
        };
        template.update(sql, data, id, user);
        return getUserById(id);
    }
}
