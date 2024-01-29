package com.example.notepadApp.service;

import com.example.notepadApp.entities.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NoteService {
    Note createNote(Note note);

    List<Note> getAllNotes();

    Note getNoteById(Integer id);

    void deleteAllNotes();

    void deleteById(Integer id);

    Note updateNote(Integer id, Note note);

}
