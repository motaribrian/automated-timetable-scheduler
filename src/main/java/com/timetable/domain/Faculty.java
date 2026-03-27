package com.timetable.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "faculty")
@NoArgsConstructor
public class Faculty extends User {

    @ElementCollection
    @CollectionTable(name = "faculty_subjects", joinColumns = @JoinColumn(name = "faculty_id"))
    @Column(name = "subject_name")
    private List<String> subjects = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "faculty_preferred_slots",
            joinColumns = @JoinColumn(name = "faculty_id"),
            inverseJoinColumns = @JoinColumn(name = "timeslot_id")
    )
    private List<TimeSlot> preferredSlots = new ArrayList<>();

    private int maxHoursPerDay;

    private boolean isAvailable = true;

    // This is the "One" side of the relationship.
    // 'mappedBy' points to the field name in the Lesson class.
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Lesson> assignedLessons = new ArrayList<>();

    public Faculty(Long id, String name, String email, String password,
                   List<String> subjects, int maxHoursPerDay) {
        super(id, name, email, password);
        this.subjects = subjects;
        this.maxHoursPerDay = maxHoursPerDay;
    }
}