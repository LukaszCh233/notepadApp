package com.example.notepadApp.controllerTests;

import com.example.notepadApp.entities.Note;
import com.example.notepadApp.service.serviceImpl.NoteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc


public class NoteControllerTest {

    LocalDate date = LocalDate.now();
    @Autowired
    WebApplicationContext context;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private NoteServiceImpl noteService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser
    @Test
    void testAddNote_Success() throws Exception {


        when(noteService.createNote(any(Note.class)))
                .thenAnswer(invocation -> {
                    Note inputNote = invocation.getArgument(0);
                    inputNote.setId(1);
                    inputNote.setDate(LocalDate.now());
                    return inputNote;
                });

        mockMvc.perform(post("/note/addNote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test Title\",\"text\":\"Test Text\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.text").value("Test Text"));
    }

    @WithMockUser
    @Test
    void testGetNoteById_Success() throws Exception {

        Note note = new Note(1, date, "testTitle", "testText");
        Note note1 = new Note(2, date, "testTitle1", "testText1");

        when(noteService.createNote(note)).thenReturn(note);
        when(noteService.createNote(note1)).thenReturn(note1);
        when(noteService.getNoteById(note.getId())).thenReturn((note));

        mockMvc.perform(MockMvcRequestBuilders.get("/note/getNote/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("testTitle"))
                .andExpect(jsonPath("$.text").value("testText"));
    }

    @WithMockUser
    @Test
    void testDeleteAll_Success() throws Exception {

        List<Note> notes = Arrays.asList(new Note(1, date, "Test1", "Text1"), new Note(2, date, "Test2", "Text2"));


        doNothing().when(noteService).deleteAllNotes();

        mockMvc.perform(MockMvcRequestBuilders.delete("/note/deleteAll"))
                .andExpect(status().isOk())
                .andExpect(content().string("All notes have been deleted"));
    }

    @WithMockUser
    @Test
    void testDeleteNoteById_Success() throws Exception {

        int existingNoteId = 1;

        doNothing().when(noteService).deleteById(existingNoteId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/note/delete/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string("Note deleted"));
    }

    @WithMockUser
    @Test
    void testUpdateNote_Success() throws Exception {

        Note existingNote = new Note(1, LocalDate.now(), "Existing Title", "Existing Text");
        Note updatedNote = new Note(1, LocalDate.now(), "Updated Title", "Updated Text");

        when(noteService.updateNote(1, existingNote)).thenReturn(updatedNote);

        mockMvc.perform(put("/note/update/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated Title\",\"text\":\"Updated Text\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
