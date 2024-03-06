package com.example.notepadApp.serviceTests;

import com.example.notepadApp.entities.Note;
import com.example.notepadApp.repository.NoteRepository;
import com.example.notepadApp.service.serviceImpl.NoteServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@AutoConfigureMockMvc
public class NoteServiceTest {
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private NoteServiceImpl noteService;

    @Test
    void shouldCreateNote_Test() {
        //Given
        Note note = new Note(null, LocalDate.now(), "testTitle", "testText");

        //When
        Note resultNote = noteService.createNote(note);

        //Then
        assertEquals(note.getId(), resultNote.getId());
        assertEquals("testTitle", resultNote.getTitle());
        assertEquals("testText", resultNote.getText());
    }

    @Test
    void shouldFindNoteById_Test() {
        //Given
        Note note = new Note(null, LocalDate.now(), "testTitle", "testText");

        noteRepository.save(note);

        //When
        Note resultNote = noteService.getNoteById(note.getId());

        //Then
        assertEquals(note.getId(), resultNote.getId());
        assertEquals(note.getText(), resultNote.getText());
        assertEquals(note.getTitle(), resultNote.getTitle());
    }

    @Test
    void shouldFindAllNotes_Test() {
        // Given
        List<Note> notes = Arrays.asList(
                new Note(null, LocalDate.now(), "testTitle", "testText"),
                new Note(null, LocalDate.now(), "testTitle1", "testText1")
        );

        noteRepository.saveAll(notes);

        // When
        List<Note> noteList = noteService.getAllNotes();

        // Then
        assertEquals(2, noteList.size());
        assertEquals(noteList.get(0).getTitle(), notes.get(0).getTitle());
        assertEquals(noteList.get(1).getTitle(), notes.get(1).getTitle());
        assertEquals(noteList.get(0).getText(), notes.get(0).getText());
        assertEquals(noteList.get(1).getText(), notes.get(1).getText());
    }

    @Test
    void shouldDeleteNoteById_Test() {
        // Given
        Note note = new Note(null, LocalDate.now(), "testTitle", "testText");
        noteRepository.save(note);

        // When
        noteService.deleteById(note.getId());

        // Then
        assertTrue(noteRepository.findById(note.getId()).isEmpty());
    }

    @Test
    void shouldDeleteAllNotes_Test() {
        // Given
        List<Note> notes = Arrays.asList(
                new Note(null, LocalDate.now(), "testTitle", "testText"),
                new Note(null, LocalDate.now(), "testTitle11", "testText11")
        );

        noteRepository.saveAll(notes);

        // When
        noteService.deleteAllNotes();

        // Then
        List<Note> noteList = noteRepository.findAll();
        assertTrue(noteList.isEmpty());
    }

    @Test
    void shouldUpdateNote_Test() {
        //Given
        Note existNote = new Note(null, LocalDate.now(), "testTitle", "testText");
        Note updatetNote = new Note(null, LocalDate.now(), "testTitleUpdate", "testTextUpdate");

        noteRepository.save(existNote);

        //When
        Note result = noteService.updateNote(existNote.getId(), updatetNote);

        //Then
        assertEquals(updatetNote.getTitle(), result.getTitle());
        assertEquals(updatetNote.getText(), result.getText());
    }
}
