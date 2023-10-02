package com.example.notepadApp.controller;

import com.example.notepadApp.entities.Note;
import com.example.notepadApp.service.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/note")
public class NoteController {
    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }
    @PostMapping("/add")
    public ResponseEntity<?> createNote(@RequestBody Note note, Principal principal) {
        if (principal == null) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        try {
            if (noteService.existsNoteById(note.getId())) {
                logger.warn("Note with ID {} already exists.", note.getId());
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Note createdNote = noteService.createNote(note);
            logger.info("Created a new note with ID {}.", createdNote.getId());
            return new ResponseEntity<>(createdNote, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while creating a note: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getNote/{id}")
    public ResponseEntity<?> displayNote(@PathVariable Long id) {
        try {
            Optional<Note> getNoteOptional = noteService.getNoteById(id);
            if (getNoteOptional.isPresent()) {
                Note getNote = getNoteOptional.get();
                logger.info("Note {} is present", getNote);
                return new ResponseEntity<>(getNote, HttpStatus.OK);
            } else {
                logger.warn("Note with this ID not exists.");
                return new ResponseEntity<>("Note not exists", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.error("Error while display a note: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello");
    }

}
