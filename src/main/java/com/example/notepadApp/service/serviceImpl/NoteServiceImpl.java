package com.example.notepadApp.service.serviceImpl;

import com.example.notepadApp.entities.Note;
import com.example.notepadApp.repository.NoteRepository;
import com.example.notepadApp.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {
    NoteRepository noteRepository;

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
        return noteRepository.findAll();
    }

    @Override
    public Note getNoteById(Integer id) {
        return noteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found note"));
    }

    @Override
    public void deleteAllNotes() {
        List<Note> notes = getAllNotes();
        if (notes.isEmpty()) {
            throw new ResourceNotFoundException("List is empty");
        }
        noteRepository.deleteAll();
    }

    @Override
    public void deleteById(Integer id) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Note not found"));
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
