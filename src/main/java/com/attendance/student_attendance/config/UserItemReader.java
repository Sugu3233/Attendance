package com.attendance.student_attendance.config;

import java.time.LocalDate;

import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.infrastructure.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.infrastructure.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import com.attendance.student_attendance.model.User;

@Configuration
public class UserItemReader {

    @Value("${batch.file.path}")
    private String filepath;

    // public FlatFileItemReader<User> reader() {
    // // FlatFileItemReader<User> reader = new FlatFileItemReader<User>();
    // // reader.setResource(new FileSystemResource(filepath +
    // // "sample_user_data.csv"));
    // // reader.setLinesToSkip(1);

    // // Tokenizer
    // DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
    // tokenizer.setDelimiter(",");
    // tokenizer.setStrict(false); // Ignore the Missing Columns
    // tokenizer.setNames("name", "mobile_no", "password", "address", "city",
    // "district", "dob", "role",
    // "oprtnl_flag");

    // // Field Set Mapper
    // BeanWrapperFieldSetMapper<User> fieldSetMapper = new
    // BeanWrapperFieldSetMapper<>();
    // fieldSetMapper.setTargetType(User.class);

    // // Line Mapper combiles TOkenizer and Mapper
    // DefaultLineMapper<User> lineMapper = new DefaultLineMapper<>();
    // lineMapper.setLineTokenizer(tokenizer);
    // lineMapper.setFieldSetMapper(fieldSetMapper);

    // FlatFileItemReader<User> reader = new FlatFileItemReader<>(lineMapper);
    // System.out.println(filepath + "sample_user_data.csv");
    // reader.setResource(new FileSystemResource(filepath +
    // "sample_user_data.csv"));
    // reader.setLinesToSkip(1);
    // reader.setLineMapper((line, lineNumber) -> {

    // String[] data = line.split(",");

    // User user = new User();

    // user.setName(data[0]);
    // user.setMobile_no(data[1]);
    // user.setPassword(data[2]);
    // user.setAddress(data[3]);
    // user.setCity(data[4]);
    // user.setDistrict(data[5]);
    // user.setDob(LocalDate.parse(data[6]));
    // user.setRole(data[7]);
    // user.setOprtnl_flag(Boolean.parseBoolean(data[8]));

    // return user;
    // });
    // return reader;
    // }
    @Bean
    public FlatFileItemReader<User> reader() {

        FlatFileItemReader<User> reader = new FlatFileItemReader<>(
                (line, lineNumber) -> {

                    String[] data = line.split(",");

                    User user = new User();

                    user.setName(data[0]);
                    user.setMobile_no(data[1]);
                    user.setPassword(data[2]);
                    user.setAddress(data[3]);
                    user.setCity(data[4]);
                    user.setDistrict(data[5]);
                    user.setDob(LocalDate.parse(data[6]));
                    user.setRole(data[7]);
                    user.setOprtnl_flag(Boolean.parseBoolean(data[8]));

                    return user;
                });

        reader.setResource(new FileSystemResource(filepath + "/sample_user_data.csv"));
        reader.setLinesToSkip(1);

        return reader;
    }
}
