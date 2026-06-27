package com.attendance.student_attendance.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.attendance.student_attendance.model.User;
import com.attendance.student_attendance.repository.UserRepository;

@Component
public class UserItemWriter implements ItemWriter<User> {

    @Autowired
    private UserRepository repo;

    @Override
    public void write(Chunk<? extends User> chunk) throws Exception {
        List<User> users = new ArrayList<>(chunk.getItems());

        repo.saveAll(users);

    }

}
