package com.example.user_menagement.service;

import com.example.user_menagement.entity.RunningNumber;
import com.example.user_menagement.repository.RunningNumberRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RunningNumberService {
    private final RunningNumberRepo runningNumberRepo;
    public RunningNumberService(RunningNumberRepo runningNumberRepo) {
        this.runningNumberRepo = runningNumberRepo;
    }

    @Transactional
    public String generateUserId(String prefix) {

        RunningNumber rn = runningNumberRepo.findByPrefix(prefix)
                .orElseGet(() -> {
                    RunningNumber n = new RunningNumber();
                    n.setPrefix(prefix);
                    n.setLastNumber(0);
                    return runningNumberRepo.save(n);
                });

        int current = rn.getLastNumber() == null ? 0 : rn.getLastNumber();
        int next = current + 1;

        return String.format("%03d", next);
    }


}
