package com.example.user_menagement.service;

import com.example.user_menagement.entity.Privilege;
import com.example.user_menagement.entity.User;
import com.example.user_menagement.model.PrivilegeModel;
import com.example.user_menagement.model.RePasswordModel;
import com.example.user_menagement.model.SerchUserModel;
import com.example.user_menagement.model.UserModel;
import com.example.user_menagement.repository.RunningNumberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.user_menagement.repository.PrivilegeRepo;
import com.example.user_menagement.repository.UserRepo;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final PrivilegeRepo privilegeRepo;
    private final RunningNumberService runningNumberService;
    private final RunningNumberRepo runningNumberRepo;

    public UserService(UserRepo userRepo,
                       PrivilegeRepo privilegeRepo,
                       RunningNumberService runningNumberService, RunningNumberRepo runningNumberRepo) {
        this.userRepo = userRepo;
        this.privilegeRepo = privilegeRepo;
        this.runningNumberService = runningNumberService;
        this.runningNumberRepo = runningNumberRepo;
    }

    @Transactional
    public UserModel registerUser(UserModel userModel,
                                  PrivilegeModel privilegeModel){
        String role = privilegeModel.getAuthority();
        String prefix = role.replace("ROLE_", "");

        String userId = runningNumberService.generateUserId(prefix);
        User user = new User();
        mapUser(user, userModel);
        user.setUserId(userId);

        Privilege privilege = new Privilege();
        mapPrivilege(privilege, privilegeModel);

        privilege.setUser(user);
        user.setPrivilege(privilege);
        userRepo.save(user);
        return userModel;
    }

    @Transactional
    public Boolean rePassword(RePasswordModel rePasswordModel){
        User user = userRepo.findByUserId(rePasswordModel.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Privilege privilege = user.getPrivilege();

        if (privilege == null){
            throw new RuntimeException("Privilege not found");
        }
        if (!privilege.getPassword().equals(rePasswordModel.getRePassword())){
            privilege.setPassword(rePasswordModel.getRePassword());
            privilegeRepo.save(privilege);
        }
        return true;
    }

    @Transactional
    public List<User> searchUser(SerchUserModel serchUserModel){
        List<User> users = new ArrayList<>();
        if (serchUserModel.getUsername() != null){
            User user = userRepo.findByUserId(serchUserModel.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            users.add(user);
        }

        if (serchUserModel.getFullName() != null){
            List<User> user = userRepo.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(serchUserModel.getFullName(), serchUserModel.getFullName());
            if (user.isEmpty()) {
                throw new RuntimeException("User not found");
            }
            users.add(user.getFirst());
        }

        if (serchUserModel.getAuthority() != null){
            List<Privilege> privileges = privilegeRepo.findByAuthority(serchUserModel.getAuthority());
            if (!privileges.isEmpty()) {
                List<User> user = privileges.stream()
                        .map(Privilege::getUser)
                        .toList();
                users.add(user.getFirst());
            }

        }

        if (serchUserModel.getStatus() == 0 || serchUserModel.getStatus() == 1){
            List<Privilege> privileges = privilegeRepo.findByStatus(serchUserModel.getStatus());
            if (!privileges.isEmpty()) {
                List<User> user = privileges.stream()
                        .map(Privilege::getUser)
                        .toList();
                users.add(user.getFirst());
            }
        }
        return users;
    }

    @Transactional
    public UserModel updateUser(String userId,
                           UserModel userModel,
                           PrivilegeModel privilegeModel){
        User user = userRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        mapUpdate(user, userModel, user.getPrivilege(), privilegeModel);
        userRepo.save(user);
        return userModel;
    }

    @Transactional
    public String deleteUser(String userId){
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepo.delete(user);

        return "Delete user " + userId + " success";
    }

    private void mapUser(User user, UserModel userModel){
        user.setNid(userModel.getNid());
        user.setTitleName(userModel.getTitleName());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setPositionName(userModel.getPositionName());
        user.setDeptName(userModel.getDeptName());
        user.setContactNo(userModel.getContactNo());
        user.setEmail(userModel.getEmail());
    }
    private void mapPrivilege(Privilege privilege, PrivilegeModel privilegeModel){
        privilege.setUsername(privilegeModel.getUsername());
        privilege.setPassword(privilegeModel.getPassword());
        privilege.setBeginDate(privilegeModel.getBeginDate());
        privilege.setEndDate(privilegeModel.getEndDate());
        privilege.setAuthority(privilegeModel.getAuthority());
        privilege.setStatus(privilegeModel.getStatus());
    }
    private void mapUpdate(User user, UserModel userModel,Privilege privilege, PrivilegeModel privilegeModel){
        if (userModel.getUserId() != null){user.setUserId(userModel.getUserId());}
        if (userModel.getNid() != null){user.setNid(userModel.getNid());}
        if (userModel.getTitleName() != null){user.setTitleName(userModel.getTitleName());}
        if (userModel.getFirstName() != null){user.setFirstName(userModel.getFirstName());}
        if (userModel.getLastName() != null){user.setLastName(userModel.getLastName());}
        if (userModel.getPositionName() != null){user.setPositionName(userModel.getPositionName());}
        if (userModel.getDeptName() != null){user.setDeptName(userModel.getDeptName());}
        if (userModel.getContactNo() != 0){user.setContactNo(userModel.getContactNo());}
        if (userModel.getEmail() != null){user.setEmail(userModel.getEmail());}

        if (privilegeModel.getUsername() != null){privilege.setUsername(privilegeModel.getUsername());}
        if (privilegeModel.getPassword() != null){privilege.setPassword(privilegeModel.getPassword());}
        if (privilegeModel.getBeginDate() != null){privilege.setBeginDate(privilegeModel.getBeginDate());}
        if (privilegeModel.getEndDate() != null){privilege.setEndDate(privilegeModel.getEndDate());}
        if (privilegeModel.getAuthority() != null){privilege.setAuthority(privilegeModel.getAuthority());}
        if (privilege.getStatus() != null){privilege.setStatus(privilegeModel.getStatus());}
    }

    public String getCurrentUserId(String prefix) {
        return runningNumberRepo.findByPrefix(prefix)
                .map(rn -> String.format("%03d", rn.getLastNumber() + 1))
                .orElse("001");
    }

}