package com.attendance.student_attendance.model;

import java.time.LocalDate;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Scope("prototype")
@Component
public class User {

    private Long id;

    private String name;

    private String mobile_no;

    private String password;

    private String address;

    private String city;

    private String district;

    private LocalDate dob;

    private String role;

    private Boolean oprtnl_flag;

}
