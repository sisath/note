package com.example.note.service;

import com.example.note.model.Note;
import com.example.note.repository.NoteRepository;
import com.example.note.utils.Assert;
import com.example.note.utils.LoggerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService {

    private static final String NOTE_ID_PREFIX = "Note with ID ";

    private final NoteRepository noteRepository;

    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = Assert.requireNotNull(noteRepository, "Repository cannot be null");
    }

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

    @Override
    public Note getNoteById(Long id) {
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

    @Override
    public Note updateNote(Long id, Note note) {
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

    @Override
    public void deleteNoteById(Long id) {
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