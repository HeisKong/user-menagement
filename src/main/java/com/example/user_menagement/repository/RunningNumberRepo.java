package com.example.user_menagement.repository;

import com.example.user_menagement.entity.RunningNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RunningNumberRepo extends JpaRepository<RunningNumber, String> {
    Optional<RunningNumber> findByPrefix(String prefix);
}
