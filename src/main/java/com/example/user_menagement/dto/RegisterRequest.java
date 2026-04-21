package com.example.user_menagement.dto;

import com.example.user_menagement.model.PrivilegeModel;
import com.example.user_menagement.model.UserModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RegisterRequest {
    private UserModel user;
    private PrivilegeModel privilege;
}