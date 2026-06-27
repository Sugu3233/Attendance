package com.attendance.student_attendance.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;

import com.attendance.student_attendance.model.User;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private UserItemReader reader;

    @Autowired
    private UserItemProcessor processor;

    @Autowired
    private UserItemWriter writer;

    @Bean
    public Step userStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("userStep", jobRepository)
                .<User, User>chunk(10, transactionManager).reader(reader.reader()).processor(processor)
                .writer(writer).transactionManager(transactionManager).build();
    }

    @Bean
    public Job userJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("userJob", jobRepository).incrementer(new RunIdIncrementer())
                .start(userStep(jobRepository, transactionManager)).build();
    }

}
