package com.example.user_menagement.repository;

import com.example.user_menagement.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrivilegeRepo extends JpaRepository<Privilege,Integer> {
    List<Privilege> findByAuthority(String authority);

    List<Privilege> findByStatus(int status);
}
