package com.attendance.student_attendance.controller;

import java.io.File;
import java.util.List;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.attendance.student_attendance.model.User;
import com.attendance.student_attendance.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job userJob;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = service.getAllUsers();
        if (users != null && !users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/users")
    public ResponseEntity<User> saveUsers(@RequestBody User user) {
        return new ResponseEntity<>(service.saveUser(user), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = service.getUserById(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable Long id) {
        User user = service.getUserById(id);
        if (user != null) {
            User selectedUser = service.updateUserById(id, user);
            return new ResponseEntity<>(selectedUser, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/users/job")
    public ResponseEntity<String> runJob(@RequestParam("file") MultipartFile file) throws Exception {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a CSV file.");
        }

        String uploadDirectory = System.getProperty("user.dir") + "/uploads";

        File dir = new File(uploadDirectory);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        File serverFile = new File(dir, file.getOriginalFilename());

        file.transferTo(serverFile);

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("filePath", serverFile.getAbsolutePath())
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(userJob, jobParameters);

        return ResponseEntity.ok("Job submitted successfully.");
    }

}
