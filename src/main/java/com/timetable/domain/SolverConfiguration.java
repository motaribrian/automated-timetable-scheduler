package com.timetable.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Data
public class SolverConfiguration implements Serializable {
    // Getters and Setters
    private int terminationMinutes = 5;
    private int terminationSeconds = 0;
    private Integer bestScoreLimit = null;
    private Integer unimprovedSecondsLimit = 120; // 2 minutes
    
    public SolverConfiguration() {
    }
    
    public SolverConfiguration(int terminationMinutes, int terminationSeconds, 
                              Integer bestScoreLimit, Integer unimprovedSecondsLimit) {
        this.terminationMinutes = terminationMinutes;
        this.terminationSeconds = terminationSeconds;
        this.bestScoreLimit = bestScoreLimit;
        this.unimprovedSecondsLimit = unimprovedSecondsLimit;
    }

    public long getTotalTerminationSeconds() {
        return (terminationMinutes * 60L) + terminationSeconds;
    }
    
    @Override
    public String toString() {
        return "SolverConfiguration{" +
                "terminationMinutes=" + terminationMinutes +
                ", terminationSeconds=" + terminationSeconds +
                ", bestScoreLimit=" + bestScoreLimit +
                ", unimprovedSecondsLimit=" + unimprovedSecondsLimit +
                '}';
    }
}
