package com.timetable.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a lesson, including its course, batch, faculty, room, and time slot.
 * This is a @PlanningEntity used by OptaPlanner to optimize timetable scheduling.
 */
@Data
@NoArgsConstructor
@Entity
@PlanningEntity
@Slf4j
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    @PlanningId
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Course course;
    @ManyToOne(fetch = FetchType.EAGER)
    private StudentBatch studentBatch;
    private String lessonType; // Tracks whether the lesson is a LAB or LECTURE

//    @PlanningVariable(valueRangeProviderRefs = "facultyRange")
    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty teacher;

    @PlanningVariable(valueRangeProviderRefs = "roomRange")
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @PlanningVariable(valueRangeProviderRefs = "timeSlotRange")
    @ManyToOne
    @JoinColumn(name = "timeslot_id")
    private TimeSlot timeSlot;

    private TimeSlot minorTimeSlot;

    @Transient
    private List<Room> roomList=new ArrayList<>(); // List of potential rooms for the lesson

    public Lesson(Long id, Course course, StudentBatch studentBatch, List<Room> roomList) {
        this.id = id;
        this.course = course;
        this.studentBatch = studentBatch;
        this.roomList = roomList;
        this.room = null; // Room will be assigned during planning
    }

    public Lesson(Long id, Course course, List<Room> roomList) {
        this.id = id;
        this.course = course;
        this.roomList = roomList;
        this.room = null; // Room will be assigned during planning
    }



    // Helper method to check if a time slot is suitable for a lab
    private boolean isLabTimeSlot(TimeSlot timeSlot) {
        return timeSlot.getTimeSlotIndex() == (int) (id % 5) + 1; // Example logic
    }

    // Finds a room by its ID within the available room list
    private Room findRoomById(Long roomId) {
        if (roomList == null || roomId == null) return null;
        return roomList.stream()
                .filter(room -> room.getId().equals(roomId))
                .findFirst()
                .orElse(null);
    }

    // Checks if the lesson is fully assigned (faculty, room, and time slot are all set)
    public boolean isAssigned() {
        return teacher != null && room != null && timeSlot != null;
    }

    // Validates if a given faculty member can teach this course
    public boolean isValidFaculty(Faculty faculty) {
        return course != null && course.getEligibleFaculty().contains(faculty);
    }

    // Validates if the room is appropriate for the lesson type
    public boolean isValidRoom() {
        if (room == null) return false;

        if ("LAB".equals(lessonType)) {
            return room.isLabRoom() && studentBatch.getPracticalRoomIDs().contains(room.getId());
        } else {
            return room.isLectureRoom() && studentBatch.getLectureRoomIDs().contains(room.getId());
        }
    }

    public boolean hasValidBatchAndCourse() {
        return this.getStudentBatch() != null && this.getCourse() != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return Objects.equals(id, lesson.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", course=" + (course != null ? course.getCourseCode() : "null") +
                ", studentBatch=" + (studentBatch != null ? studentBatch.getBatchName() : "null") +
                ", faculty=" + (teacher != null ? teacher.getName() : "null") +
                ", room=" + (room != null ? room.getRoomNumber() : "null") +
                ", timeSlot=" + (timeSlot != null ? timeSlot.getDay() + " " + timeSlot.getStartTime() : "null") +
                '}';
    }
}
