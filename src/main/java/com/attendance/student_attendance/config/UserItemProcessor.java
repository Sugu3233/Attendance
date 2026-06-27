package com.attendance.student_attendance.config;

import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.attendance.student_attendance.model.User;

@Component
public class UserItemProcessor implements ItemProcessor<User, User> {

    @Override
    public User process(User user) throws Exception {
        // Set Values in User Entity

        return user;
    }

}
