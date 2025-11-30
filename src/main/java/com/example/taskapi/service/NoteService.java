package com.example.taskapi.service;

import com.example.taskapi.model.Note;
import com.example.taskapi.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public List<Note> getActiveNotes() {
        return noteRepository.findByArchived(false);
    }

    public Optional<Note> getNoteById(String id) {
        return noteRepository.findById(id);
    }

    public List<Note> searchByTitle(String title) {
        return noteRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Note> getNotesByTag(String tag) {
        return noteRepository.findByTagsContaining(tag);
    }

    public Note createNote(Note note) {
        note.setCreatedAt(LocalDateTime.now());
        note.setUpdatedAt(LocalDateTime.now());
        return noteRepository.save(note);
    }

    public Note updateNote(String id, Note updatedNote) {
        return noteRepository.findById(id)
                .map(note -> {
                    note.setTitle(updatedNote.getTitle());
                    note.setContent(updatedNote.getContent());
                    note.setTags(updatedNote.getTags());
                    note.setUpdatedAt(LocalDateTime.now());
                    return noteRepository.save(note);
                })
                .orElseThrow(() -> new RuntimeException("Note not found"));
    }

    public Note archiveNote(String id) {
        return noteRepository.findById(id)
                .map(note -> {
                    note.setArchived(true);
                    note.setUpdatedAt(LocalDateTime.now());
                    return noteRepository.save(note);
                })
                .orElseThrow(() -> new RuntimeException("Note not found"));
    }

    public void deleteNote(String id) {
        noteRepository.deleteById(id);
    }
}