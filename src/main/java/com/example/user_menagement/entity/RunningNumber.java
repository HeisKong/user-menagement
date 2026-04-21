package com.example.user_menagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "running_number")
@Getter
@Setter
public class RunningNumber {
    @Id
    private String prefix;
    @Column(nullable = false)
    private Integer lastNumber;
}
