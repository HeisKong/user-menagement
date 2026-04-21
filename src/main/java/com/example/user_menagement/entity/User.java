package com.example.user_menagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @Column(length = 20)
    private String userId;
    private String nid;
    private String titleName;
    private String firstName;
    private String lastName;
    private String positionName;
    private String deptName;
    private int contactNo;
    private String email;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Privilege privilege;

}
