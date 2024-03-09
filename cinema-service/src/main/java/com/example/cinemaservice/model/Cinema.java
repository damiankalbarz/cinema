package com.example.cinemaservice.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "cinema")
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cinema_id")
    private int id;
    @NotBlank(message = "Nazwa kina nie może być pusta")
    private String cinemaName;

    @NotBlank(message = "Lokalizacja kina nie może być pusta")
    private String location;


    //@JoinColumn(name="cinema_id")
    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> roomList= new ArrayList<>();


    public void addRoom(Room room){
        roomList.add(room);
        room.setCinema(this);
        }
}
