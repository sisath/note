package com.example.note.controller;

import com.example.note.model.Note;
import com.example.note.service.NoteService;
import com.example.note.utils.Assert;
import com.example.note.utils.LoggerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = Assert.requireNotNull(noteService, "Note service cannot be null");
    }

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        try {
            Note createdNote = noteService.createNote(note);
            LoggerService.logInfo("Note created: " + createdNote.getId());
            return new ResponseEntity<>(createdNote, HttpStatus.CREATED);
        } catch (Exception e) {
            LoggerService.logError("Error creating note", e);
            throw e;
        }
    }

    @GetMapping
    public List<Note> getAllNotes() {
        List<Note> notes = noteService.getAllNotes();
        LoggerService.logInfo("Retrieved all notes, count: " + notes.size());
        return notes;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        Note note = noteService.getNoteById(id);
        if (note != null) {
            LoggerService.logInfo("Retrieved note by ID: " + id);
            return ResponseEntity.ok(note);
        } else {
            LoggerService.logInfo("Note not found with ID: " + id);
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody Note note) {
        try {
            Note updatedNote = noteService.updateNote(id, note);
            LoggerService.logInfo("Note updated: " + id);
            return ResponseEntity.ok(updatedNote);
        } catch (Exception e) {
            LoggerService.logError("Error updating note with ID: " + id, e);
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        try {
            noteService.deleteNoteById(id);
            LoggerService.logInfo("Note deleted: " + id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            LoggerService.logError("Error deleting note with ID: " + id, e);
            throw e;
        }
    }
}
