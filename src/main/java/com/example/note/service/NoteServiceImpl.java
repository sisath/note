package com.example.note.service;

import com.example.note.model.Note;
import com.example.note.repository.NoteRepository;
import com.example.note.utils.Assert;
import com.example.note.utils.LoggerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of the NoteService interface providing CRUD operations for notes.
 */
@Service
public class NoteServiceImpl implements NoteService {

    private static final String NOTE_ID_PREFIX = "Note with ID ";

    private final NoteRepository noteRepository;

    /**
     * Constructs a new NoteServiceImpl with the specified NoteRepository.
     *
     * @param noteRepository The NoteRepository used for data access.
     * @throws IllegalArgumentException if noteRepository is null.
     */
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = Assert.requireNotNull(noteRepository, "Repository cannot be null");
    }

    /**
     * Creates a new note.
     *
     * @param note The note object to be created.
     * @return The created note.
     */
    @Override
    public Note createNote(Note note) {
        try {
            LoggerService.logInfo("Creating a new note.");
            if (note.getId() != null) {
                throw new IllegalArgumentException("ID must be null for new note creation.");
            }
            Note createdNote = noteRepository.save(note);
            LoggerService.logInfo("Created new note with ID: " + createdNote.getId());
            return createdNote;
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
    @Override
    public List<Note> getAllNotes() {
        try {
            LoggerService.logInfo("Fetching all notes from the repository.");
            List<Note> notes = noteRepository.findAll();
            LoggerService.logInfo("Fetched " + notes.size() + " notes from the repository.");
            return notes;
        } catch (Exception e) {
            LoggerService.logError("Error fetching all notes", e);
            throw e;
        }
    }

    /**
     * Retrieves a note by its ID.
     *
     * @param id The ID of the note to retrieve.
     * @return The retrieved note, or null if not found.
     */
    @Override
    public Note getNoteById(UUID id) {
        try {
            LoggerService.logInfo(NOTE_ID_PREFIX + id + " from the repository.");
            Optional<Note> noteOptional = noteRepository.findById(id);
            if (noteOptional.isPresent()) {
                Note note = noteOptional.get();
                LoggerService.logInfo(NOTE_ID_PREFIX + id + " found in the repository.");
                return note;
            } else {
                LoggerService.logInfo(NOTE_ID_PREFIX + id + " not found in the repository.");
                return null;
            }
        } catch (Exception e) {
            LoggerService.logError("Error fetching note by ID: " + id, e);
            throw e;
        }
    }

    /**
     * Updates an existing note.
     *
     * @param id   The ID of the note to update.
     * @param note The updated note object.
     * @return The updated note.
     */
    @Override
    public Note updateNote(UUID id, Note note) {
        try {
            LoggerService.logInfo("Updating note with ID: " + id);
            if (!noteRepository.existsById(id)) {
                throw new IllegalArgumentException(NOTE_ID_PREFIX + id + " does not exist.");
            }
            note.setId(id);
            Note updatedNote = noteRepository.save(note);
            LoggerService.logInfo("Updated note with ID: " + id);
            return updatedNote;
        } catch (Exception e) {
            LoggerService.logError("Error updating note with ID: " + id, e);
            throw e;
        }
    }

    /**
     * Deletes a note by its ID.
     *
     * @param id The ID of the note to delete.
     */
    @Override
    public void deleteNoteById(UUID id) {
        try {
            LoggerService.logInfo("Deleting note with ID: " + id);
            noteRepository.deleteById(id);
            LoggerService.logInfo("Deleted note with ID: " + id);
        } catch (Exception e) {
            LoggerService.logError("Error deleting note with ID: " + id, e);
            throw e;
        }
    }
}