package com.example.user_menagement.controller;

import com.example.user_menagement.dto.RegisterRequest;
import com.example.user_menagement.dto.UpdateUserModel;
import com.example.user_menagement.entity.User;
import com.example.user_menagement.model.RePasswordModel;
import com.example.user_menagement.model.SerchUserModel;
import com.example.user_menagement.model.UserModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.user_menagement.service.UserService;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200/")
public class UserManage {
    private final UserService userService;

    public UserManage(UserService userService) {
        this.userService = userService;

    }

    @PostMapping("/register")
    public UserModel registerUser(@RequestBody RegisterRequest registerRequest) {
        return userService.registerUser(registerRequest.getUser(), registerRequest.getPrivilege());
    }

    @PostMapping("/rePass")
    public Boolean rePassword(@RequestBody RePasswordModel rePasswordModel){
        return userService.rePassword(rePasswordModel);
    }

    @PostMapping("/search")
    public List<User> searchUser(@RequestBody SerchUserModel serchUserModel){
        return  userService.searchUser(serchUserModel);
    }

    @PutMapping("/update/{userId}")
    public UserModel updateUser(@PathVariable String userId,
                           @RequestBody UpdateUserModel updateUserModel){
        return userService.updateUser(userId, updateUserModel.getUserModel(), updateUserModel.getPrivilegeModel());
    }

    @DeleteMapping("/delete/{userId}")
    public String deleteUser(@PathVariable String userId){
        return  userService.deleteUser(userId);
    }

    @GetMapping("/user-getId")
    public ResponseEntity<String> previewUserId(@RequestParam String role) {
        String prefix = role.replace("ROLE_", "");
        String nextId = userService.getCurrentUserId(prefix);
        return ResponseEntity.ok(nextId);
    }

}
