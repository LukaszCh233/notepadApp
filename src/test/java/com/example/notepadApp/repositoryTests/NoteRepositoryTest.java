package com.example.notepadApp.repositoryTests;

import com.example.notepadApp.entities.Note;
import com.example.notepadApp.repository.NoteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class NoteRepositoryTest {

    @Autowired
    NoteRepository noteRepository;
    LocalDate date = LocalDate.now();

    @BeforeEach
    public void setUp() {
        noteRepository.deleteAll();
    }

    @Test
    public void shouldSaveNoteTest() {

        //Given
        Note note = new Note(null, date, "TitleTest", "TextTest");

        //When
        noteRepository.save(note);

        //Then
        List<Note> notes = noteRepository.findAll();
        Assertions.assertFalse(notes.isEmpty());
    }

    @Test
    public void shouldFindNoteByIdTest() {

        //Given
        Note note = new Note(1, date, "TitleTest", "TextTest");

        //When
        noteRepository.save(note);

        //Then
        Optional<Note> foundNoteOptional = noteRepository.findById(note.getId());
        Assertions.assertTrue(foundNoteOptional.isPresent());
        Integer foundNoteId = foundNoteOptional.get().getId();
        Assertions.assertEquals(foundNoteId, note.getId());

    }

    @Test
    public void shouldDeleteAllNotesTest() {

        //Given
        Note note = new Note(1, date, "TitleTest", "TextTest");
        Note note1 = new Note(2, date, "TitleTest1", "TextTest1");

        //When
        noteRepository.save(note);
        noteRepository.save(note1);

        //Then
        noteRepository.deleteAll();
        List<Note> notes = noteRepository.findAll();
        Assertions.assertTrue(notes.isEmpty());

    }

    @Test
    public void shouldDeleteNoteByIdTest() {

        //Given
        Note note = new Note(3, date, "TitleTest", "TextTest");

        //When
        noteRepository.save(note);

        //Then
        noteRepository.deleteById(note.getId());
        Optional<Note> deletedNote = noteRepository.findById(note.getId());
        Assertions.assertFalse(deletedNote.isPresent());

    }
}
