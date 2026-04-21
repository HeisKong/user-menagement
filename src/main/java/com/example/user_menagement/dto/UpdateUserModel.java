package com.example.user_menagement.dto;

import com.example.user_menagement.model.UserModel;
import lombok.Data;
import com.example.user_menagement.model.PrivilegeModel;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UpdateUserModel {
    private UserModel userModel;
    private PrivilegeModel privilegeModel;
}
