package com.attendance.student_attendance.config;

import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.attendance.student_attendance.model.User;

@Configuration
public class UserItemReader {

    @Value("${batch.file.path}")
    private String filepath;

    FlatFileItemReaderBuilder<User> reader() {
        FlatFileItemReaderBuilder<User> reader = new FlatFileItemReaderBuilder<>();
        reader.resource(new FileSystemResource(filepath + "user.csv"));
        reader.linesToSkip(1);
        return null;
    }
}
