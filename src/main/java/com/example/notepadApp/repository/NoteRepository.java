package com.example.notepadApp.repository;

import com.example.notepadApp.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note,Long> {
    boolean existsByTitle(String title);
}