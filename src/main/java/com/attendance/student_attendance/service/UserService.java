package com.attendance.student_attendance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendance.student_attendance.model.User;
import com.attendance.student_attendance.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    public List<User> getAllUsers() {
        return repo.getAllUsers();
    }

    public User saveUser(User user) {
        return repo.save(user);
    }

    public User getUserById(Long id) {
        return repo.getUserById(id);
    }

    public User updateUserById(Long id, User user) {
        return repo.updateUserById(id, user);
    }

}
