package com.example.notepadApp.service.serviceImpl;

import com.example.notepadApp.entities.Note;
import com.example.notepadApp.repository.NoteRepository;
import com.example.notepadApp.service.NoteService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public Note createNote(Note note) {
        note.setDate(LocalDate.now());
        return noteRepository.save(note);
    }

    @Override
    public List<Note> getAllNotes() {
        List<Note> notes = noteRepository.findAll();
        if (notes.isEmpty()) {
            throw new EntityNotFoundException("List is empty");
        }
        return notes;
    }

    @Override
    public Note getNoteById(Integer id) {
        return noteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found note"));
    }

    @Override
    public void deleteAllNotes() {
        getAllNotes();
        noteRepository.deleteAll();
    }

    @Override
    public void deleteById(Integer id) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Note not found"));
        noteRepository.delete(note);
    }

    @Override
    public Note updateNote(Integer id, Note note) {
        Note presentNote = getNoteById(id);
        presentNote.setTitle(note.getTitle());
        presentNote.setText(note.getText());
        return noteRepository.save(presentNote);
    }
}
