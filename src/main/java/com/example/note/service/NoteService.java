package com.example.note.service;

import com.example.note.model.Note;

import java.util.List;

public interface NoteService {

    List<Note> getAllNotes();

    Note getNoteById(Long id);

    Note createNote(Note note);

    Note updateNote(Long id, Note note);

    void deleteNoteById(Long id);
}
