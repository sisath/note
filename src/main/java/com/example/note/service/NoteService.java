package com.example.note.service;

import com.example.note.model.Note;

import java.util.List;
import java.util.UUID;

public interface NoteService {

    List<Note> getAllNotes();

    Note getNoteById(UUID id);

    Note createNote(Note note);

    Note updateNote(UUID id, Note note);

    void deleteNoteById(UUID id);
}