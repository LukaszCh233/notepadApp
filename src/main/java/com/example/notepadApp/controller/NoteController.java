package com.example.notepadApp.controller;

import com.example.notepadApp.entities.Note;
import com.example.notepadApp.service.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @PostMapping("/addNote")
    public ResponseEntity<?> addNote(@RequestBody Note note) {
        try {

            Note createdNote = noteService.createNote(note);
            logger.info("Created a new note with ID {}.", createdNote.getId());
            return new ResponseEntity<>(createdNote, HttpStatus.CREATED);

        } catch (Exception e) {
            logger.error("Error while creating a note: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getNote/{id}")
    public ResponseEntity<?> displayNote(@PathVariable Integer id) {
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

    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAllNotes() {
        List<Note> allNotes = noteService.getAllNotes();
        if (allNotes.isEmpty()) {
            return new ResponseEntity<>("Notes list is empty", HttpStatus.NOT_FOUND);
        } else {
            noteService.deleteAllNotes();
            return new ResponseEntity<>("All notes have been deleted", HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        if (noteService.existsNoteById(id)) {
            noteService.deleteById(id);
            return new ResponseEntity<>("Note deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Note not exists", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateNote(@PathVariable Integer id, @RequestBody Note note) {
        try {
            Optional<Note> getNoteOptional = noteService.getNoteById(id);
            if (getNoteOptional.isPresent()) {
                Note existingNote = getNoteOptional.get();
                existingNote.setTitle(note.getTitle());
                existingNote.setText(note.getText());
                Note updatedNote = noteService.updateNote(existingNote);
              return  new ResponseEntity<>(updatedNote, HttpStatus.OK);
            } else {
               return new ResponseEntity<>("Note not exists", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}