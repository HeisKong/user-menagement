package com.example.user_menagement.model;

import lombok.Data;

@Data
public class UserModel {
    private String userId;
    private String nid;
    private String titleName;
    private String firstName;
    private String lastName;
    private String positionName;
    private String deptName;
    private int contactNo;
    private String email;
}
