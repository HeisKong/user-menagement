package com.example.user_menagement.model;

import lombok.Data;

@Data
public class SerchUserModel {
    private String username;
    private String fullName;
    private String authority;
    private int status;
}
