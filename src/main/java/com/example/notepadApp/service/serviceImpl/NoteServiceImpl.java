package com.example.notepadApp.service.serviceImpl;

import com.example.notepadApp.entities.Note;
import com.example.notepadApp.repository.NoteRepository;
import com.example.notepadApp.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class NoteServiceImpl implements NoteService {
    NoteRepository noteRepository;

    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public Note createNote(Note note) {
        return noteRepository.save(note);
    }

    @Autowired

    @Override
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    @Override
    public Optional<Note> getNoteById(Long id) {
        return noteRepository.findById(id);
    }

    @Override
    public void deleteAllNotes() {
        noteRepository.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
    noteRepository.deleteById(id);
    }

    @Override
    public Note updateNote(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public boolean existsNoteByTitle(String title) {
        return noteRepository.existsByTitle(title);
    }

    @Override
    public boolean existsNoteById(Long id) {
        return noteRepository.existsById(id);
    }


}
