package com.example.notepadApp.controller;

import com.example.notepadApp.entities.Note;
import com.example.notepadApp.service.serviceImpl.NoteServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/note")
public class NoteController {
    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);
    private final NoteServiceImpl noteService;

    @Autowired
    public NoteController(NoteServiceImpl noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/addNote")
    public ResponseEntity<Note> addNote(@RequestBody Note note) {

        Note createdNote = noteService.createNote(note);
        logger.info("Created a new note with ID {}.", createdNote.getId());
        return ResponseEntity.ok(createdNote);
    }

    @GetMapping("/getNote/{id}")
    public ResponseEntity<Note> displayNote(@PathVariable Integer id) {

        Note note = noteService.getNoteById(id);
        logger.info("Note exists");
        return ResponseEntity.ok(note);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllNotes() {

        noteService.deleteAllNotes();
        logger.info("All notes have been deleted.");
        return ResponseEntity.ok("All notes have been deleted");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {

        noteService.deleteById(id);
        logger.info("Note with ID {} has been deleted.", id);
        return ResponseEntity.ok("Note deleted");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Integer id, @RequestBody Note note) {

        Note updatedNote = noteService.updateNote(id, note);
        logger.info("Note with ID {} has been updated.", id);
        return ResponseEntity.ok(updatedNote);
    }
}