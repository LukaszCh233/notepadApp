package com.example.notepadApp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "notes")
@Getter
@Setter
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @Column (name="title")
    String title;
    @Column (name="text")
    String text;

    public Note(long id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
    }

    public Note() {
    }

}
