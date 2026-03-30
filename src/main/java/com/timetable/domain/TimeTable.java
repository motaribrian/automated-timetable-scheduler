package com.timetable.domain;

import lombok.Data;
import lombok.Getter;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.List;
import java.util.Objects;
import java.util.ArrayList;

@Data
@PlanningSolution
public class TimeTable {
    private Long id;

    @PlanningEntityCollectionProperty
    private List<Lesson> lessonList;

    @PlanningEntityCollectionProperty
    private List<Lesson> minorLessonList;

    @ValueRangeProvider(id = "teacherRange")
    @ProblemFactCollectionProperty
    private List<Faculty> facultyList;

    @ValueRangeProvider(id = "roomRange")
    @ProblemFactCollectionProperty
    private List<Room> roomList;

    @ValueRangeProvider(id = "timeSlotRange")
    @ProblemFactCollectionProperty
    private List<TimeSlot> timeSlotList;

    @ValueRangeProvider(id = "minorTimeSlotRange")
    @ProblemFactCollectionProperty
    private List<TimeSlot> minorTimeSlotList;

    @PlanningScore
    private HardSoftScore score;

    public TimeTable() {}

//    public TimeTable(Long id, List<Lesson> lessonList,List<Lesson> minorLessonList, List<Faculty> facultyList,
//                     List<Room> roomList, List<TimeSlot> timeSlotList) {
//        this.id = id;
//        this.lessonList = lessonList;
//        this.minorLessonList = minorLessonList;
//        this.facultyList = facultyList;
//        this.roomList = roomList;
//        this.timeSlotList = timeSlotList;
//    }
    public TimeTable(Long id, List<Lesson> lessonList,List<Lesson> minorLessonList, List<Faculty> facultyList,
                     List<Room> roomList, List<TimeSlot> timeSlotList, List<TimeSlot> minorTimeSlotList) {
        this.id = id;
        this.lessonList = lessonList;
        this.minorLessonList = minorLessonList;
        this.facultyList = facultyList;
        this.roomList = roomList;
        this.timeSlotList = timeSlotList;
        this.minorTimeSlotList = minorTimeSlotList;
    }


    public void setMinorTimeSlotList(List<TimeSlot> minorTimeSlotList) {
        this.minorTimeSlotList = minorTimeSlotList != null ? minorTimeSlotList : new ArrayList<>();
    }


    // Overriding equals and hashCode to compare TimeTables by id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeTable)) return false;
        TimeTable timeTable = (TimeTable) o;
        return Objects.equals(id, timeTable.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
