package com.timetable.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timeslot_id")
    private Long id;
    private String day;
    private LocalTime startTime;
    private LocalTime endTime;
    private String slotType;


    public TimeSlot(Long id, String day, LocalTime startTime, LocalTime endTime) {
        this(id, day, startTime, endTime, "LECTURE"); // Default to lecture type
    }




    // Overriding equals and hashCode to compare TimeSlots by id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeSlot)) return false;
        TimeSlot timeSlot = (TimeSlot) o;
        return Objects.equals(id, timeSlot.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getTimeSlotIndex() {
        switch (day.toLowerCase()) {
            case "monday":
                return 1;
            case "tuesday":
                return 2;
            case "wednesday":
                return 3;
            case "thursday":
                return 4;
            case "friday":
                return 5;
            default:
                throw new IllegalArgumentException("Invalid day: " + day);
        }
    }

    @Override
    public String toString() {
        return String.format("%s %s-%s (%s)",
                day,
                startTime.toString(),
                endTime.toString(),
                slotType);
    }

    public int getDurationInMinutes() {
        LocalTime startTime = this.startTime;
        LocalTime endTime = this.endTime;
        Duration duration = Duration.between(startTime, endTime);

        return (int)duration.toMinutes() % 60;
    }
}