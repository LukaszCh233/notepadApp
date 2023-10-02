package com.example.notepadApp.service;

import com.example.notepadApp.entities.Note;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface NoteService  {
    Note createNote(Note note);
    List<Note> getAllNotes();
    Optional<Note> getNoteById(Long id);
    void deleteAllNotes();
    void deleteById(Long id);
    Note updateNote(Note note);
    boolean existsNoteByTitle(String title);
    boolean existsNoteById(Long id);
}
