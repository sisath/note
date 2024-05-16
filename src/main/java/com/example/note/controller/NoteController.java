package com.example.note.controller;

import com.example.note.model.Note;
import com.example.note.service.NoteService;
import com.example.note.utils.Assert;
import com.example.note.utils.LoggerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

/**
 * Controller class for managing notes.
 * Handles HTTP requests related to notes including creation, retrieval, updating, and deletion.
 * Uses {@link NoteService} to perform CRUD operations on notes.
 */
@CrossOrigin
@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    /**
     * Constructs a new NoteController with the specified NoteService.
     *
     * @param noteService The NoteService to be used for handling note operations.
     * @throws IllegalArgumentException if noteService is null.
     */
    public NoteController(NoteService noteService) {
        this.noteService = Assert.requireNotNull(noteService, "Note service cannot be null");
    }

    /**
     * Creates a new note.
     *
     * @param note The note object to be created.
     * @return ResponseEntity with the created note and HTTP status CREATED.
     * @throws Exception If an error occurs during note creation.
     */
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

    /**
     * Retrieves all notes.
     *
     * @return List of all notes.
     */
    @GetMapping
    public List<Note> getAllNotes() {
        List<Note> notes = noteService.getAllNotes();
        LoggerService.logInfo("Retrieved all notes, count: " + notes.size());
        return notes;
    }

    /**
     * Retrieves a note by its ID.
     *
     * @param id The ID of the note to retrieve.
     * @return ResponseEntity with the retrieved note and HTTP status OK if found,
     *         otherwise ResponseEntity with HTTP status NOT_FOUND.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable UUID id) {
        Note note = noteService.getNoteById(id);
        if (note != null) {
            LoggerService.logInfo("Retrieved note by ID: " + id);
            return ResponseEntity.ok(note);
        } else {
            LoggerService.logInfo("Note not found with ID: " + id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Updates an existing note.
     *
     * @param id   The ID of the note to update.
     * @param note The updated note object.
     * @return ResponseEntity with the updated note and HTTP status OK.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable UUID id, @RequestBody Note note) {
        try {
            Note updatedNote = noteService.updateNote(id, note);
            LoggerService.logInfo("Note updated: " + id);
            return ResponseEntity.ok(updatedNote);
        } catch (Exception e) {
            LoggerService.logError("Error updating note with ID: " + id, e);
            throw e;
        }
    }

    /**
     * Deletes a note by its ID.
     *
     * @param id The ID of the note to delete.
     * @return ResponseEntity with HTTP status NO_CONTENT.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable UUID id) {
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
