package com.timetable.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rooms")
public class Room {
    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomNumber;
    private int capacity;
    private RoomType roomType; // Room type now includes expanded types
    private boolean isAvailable;


    public Room(Long id, String roomNumber, int capacity, RoomType roomType) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.roomType = roomType;
    }





    public boolean isLectureRoom() {
        return roomType == RoomType.LECTURE_ROOM;
    }

    public boolean isLabRoom() {
        return roomType == RoomType.COMPUTER_LAB || roomType == RoomType.HARDWARE_LAB;
    }

    public Integer getIdealDailyUsage() {
        if(isLectureRoom()) return 5;
        else return 2;
    }
}