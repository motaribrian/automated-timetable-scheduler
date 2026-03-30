// StudentBatch class representing a batch of students with additional room information
package com.timetable.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Data
@NoArgsConstructor

public class StudentBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String batchName;
    private int studentBatchYear;
    private int strength;
    @ManyToMany
    @JoinTable(name = "studentbatch_course")
    @Column(name = "course_id")
    private List<Course> courses;
    @ElementCollection
    private List<Long> lectureRoomIDs;     // field for lecture room IDs
    @ElementCollection
    private List<Long> practicalRoomIDs;   // field for practical (lab) room IDs


    // Constructor
    public StudentBatch(Long id, String batchName, int studentBatchYear, int strength, List<Course> courses, List<Long> lectureRoomIDs, List<Long> practicalRoomIDs) {
        this.id = id;
        this.batchName = batchName;
        this.studentBatchYear = studentBatchYear;
        this.strength = strength;
        this.courses = courses != null ? courses : new ArrayList<>();
        this.lectureRoomIDs = lectureRoomIDs != null ? lectureRoomIDs : new ArrayList<>();
        this.practicalRoomIDs = practicalRoomIDs != null ? practicalRoomIDs : new ArrayList<>();

    }

    public int getRequiredLabsPerWeek() {
        if (courses == null || courses.isEmpty()) {
            return 0; // No courses, so no labs required
        }

        int totalPracticalHours = courses.stream()
                .filter(Course::isLabCourse) // Consider only lab courses
                .mapToInt(Course::getPracticalHours)
                .sum();

        return totalPracticalHours;
    }
}
