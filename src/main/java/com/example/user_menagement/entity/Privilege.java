package com.example.user_menagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "privilege")
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int privilegeId;
    private String username;
    private String password;
    private Date beginDate;
    private Date endDate;
    private String authority;
    private Integer status;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
