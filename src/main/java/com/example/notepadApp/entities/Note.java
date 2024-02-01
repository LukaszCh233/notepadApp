package com.example.notepadApp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Table(name = "notes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "title")
    private String title;
    @Column(name = "text")
    private String text;

}
