package com.example.user_menagement.model;

import lombok.Data;

import java.util.Date;

@Data
public class PrivilegeModel {
    private String username;
    private String password;
    private Date beginDate;
    private Date endDate;
    private String authority;
    private int status;
}

